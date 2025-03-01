package fr.eirb.cassis.manager

import fr.eirb.cassis.{CAS, Hash, URL, Usage}
import fr.eirb.cassis.mapper.ProtectedURL
import fr.eirb.cassis.mapper.ProtectedURL.NormalURL
import io.github.iltotore.iron.autoRefine

import java.security.MessageDigest
import java.time.Instant

case class ProtectorManager(urls: Seq[ProtectedURL], urlCounter: Int):

  def protectURL(owner: String, url: String, now: Instant): Either[String, ProtectorManager] =
    for
      url: URL <- URL.either(url)
      cas: CAS <- CAS.either(owner)
      hash: Hash <- Hash.either(MessageDigest.getInstance("MD5").digest((now.getEpochSecond.toString + owner + url).getBytes).mkString(""))
    yield ProtectorManager(NormalURL(cas, url, hash, Usage(0), None, None) +: urls, urlCounter + 1)

  def updateURL(hash: Hash, mapper: Function[ProtectedURL, ProtectedURL]): ProtectorManager =
    ProtectorManager(urls.map(url => if url.hash == hash then mapper(url) else url), urlCounter)

  def getURL(hash: Hash): Option[ProtectedURL] =
    urls.find(url => url.hash == hash)

  def unprotectURL(hash: Hash): ProtectorManager =
    ProtectorManager(urls.filterNot(_.hash == hash), urlCounter - 1)
