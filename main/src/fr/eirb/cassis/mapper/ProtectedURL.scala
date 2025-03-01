package fr.eirb.cassis.mapper

import fr.eirb.cassis.mapper.ProtectedURL.{NormalURL, WhitelistedURL}
import fr.eirb.cassis.{CAS, Hash, URL, Usage}
import fr.eirb.cassis.Usage.+
import io.github.iltotore.iron.{:|, autoRefine}

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

  extension (whitelist: WhitelistedURL)

    def addWhitelist(access: CAS): WhitelistedURL =
      WhitelistedURL(cas, access +: whitelist.allowed, url, hash, usages, usageLimit, expiration)

  def addOneToUsage(): ProtectedURL =
    copy(usages + 1, usageLimit, expiration)

  def setUsageLimit(usageLimit: Option[Usage]): ProtectedURL =
    copy(usages, usageLimit, expiration)

  def setExpiration(expiration: Option[Instant]): ProtectedURL =
    copy(usages, usageLimit, expiration)

  private def copy(usages: Usage, usageLimit: Option[Usage], expiration: Option[Instant]): ProtectedURL =
    this match
      case NormalURL(cas, url, hash, _, _, _) => NormalURL(cas, url, hash, usages, usageLimit, expiration)
      case WhitelistedURL(cas, allowed, url, hash, _, _, _) =>
        WhitelistedURL(cas, allowed, url, hash, usages, usageLimit, expiration)

  def toWhitelist: WhitelistedURL =
    this match
      case NormalURL(cas, url, hash, usages, usageLimit, expiration) => WhitelistedURL(cas, Seq(), url, hash, usages, usageLimit, expiration)
      case whitelistedURL: WhitelistedURL                            => whitelistedURL

  def toNormal: NormalURL =
    this match
      case normal: NormalURL                                                 => normal
      case WhitelistedURL(cas, _, url, hash, usages, usageLimit, expiration) => NormalURL(cas, url, hash, usages, usageLimit, expiration)
