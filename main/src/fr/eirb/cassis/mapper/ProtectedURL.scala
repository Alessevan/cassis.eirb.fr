package fr.eirb.cassis.mapper

import fr.eirb.cassis.mapper.ProtectedURL.{NormalURL, WhitelistedURL}
import fr.eirb.cassis.{CAS, Hash, URL, Usage}
import fr.eirb.cassis.Usage.+
import io.github.iltotore.iron.{:|, autoRefine}

import java.security.MessageDigest
import java.time.Instant

enum ProtectedURL:

  def cas: CAS

  def url: URL

  def hash: Hash

  def usages: Usage

  def usageLimit: Option[Usage]

  def expiration: Option[Instant]

  case NormalURL(
    cas: CAS,
    url: URL,
    hash: Hash,
    usages: Usage = Usage(0),
    usageLimit: Option[Usage] = None,
    expiration: Option[Instant] = None
  ) extends ProtectedURL
  case WhitelistedURL(
    cas: CAS,
    allowed: Seq[CAS] = Seq(),
    url: URL,
    hash: Hash,
    usages: Usage = Usage(0),
    usageLimit: Option[Usage] = None,
    expiration: Option[Instant] = None
  ) extends ProtectedURL

  def addOneToUsage(): ProtectedURL =
    this match
      case NormalURL(cas, url, hash, usages, usageLimit, expiration) => NormalURL(cas, url, hash, usages + 1, usageLimit, expiration)
      case WhitelistedURL(cas, allowed, url, hash, usages, usageLimit, expiration) =>
        WhitelistedURL(cas, allowed, url, hash, usages + 1, usageLimit, expiration)

  def setUsageLimit(usageLimit: Usage): ProtectedURL =
    this match
      case NormalURL(cas, url, hash, _, usageLimit, expiration) => NormalURL(cas, url, hash, usages, usageLimit, expiration)
      case WhitelistedURL(cas, allowed, url, hash, _, usageLimit, expiration) =>
        WhitelistedURL(cas, allowed, url, hash, usages, usageLimit, expiration)

  def toWhitelist: WhitelistedURL =
    this match
      case NormalURL(cas, url, hash, usages, usageLimit, expiration) => WhitelistedURL(cas, Seq(), url, hash, usages, usageLimit, expiration)
      case whitelistedURL: WhitelistedURL                            => whitelistedURL

  def toNormal: NormalURL =
    this match
      case normal: NormalURL                                                 => normal
      case WhitelistedURL(cas, _, url, hash, usages, usageLimit, expiration) => NormalURL(cas, url, hash, usages, usageLimit, expiration)

def protectURL(url: String, owner: String): Either[String, ProtectedURL] =
  for
    url: URL <- URL.either(url)
    cas: CAS <- CAS.either(owner)
    hash: Hash <- Hash.either(MessageDigest.getInstance("MD5").digest(url.getBytes).mkString(""))
  yield NormalURL(cas, url, hash, Usage(0), None, None)
