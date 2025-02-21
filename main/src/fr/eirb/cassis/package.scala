package fr.eirb.cassis

import io.github.iltotore.iron.constraint.string.{Blank, Match}
import io.github.iltotore.iron.*
import io.github.iltotore.iron.constraint.numeric.*

opaque type CasRegEx =
  DescribedAs[
    Match[
      "^[a-z]+[0-9]*$"
    ],
    "A CAS must only be alphabetic characters followed by numbers."
  ]
opaque type CAS = String :| CasRegEx
object CAS extends RefinedTypeOps[String, CasRegEx, CAS]

opaque type URLRegex =
  DescribedAs[
    Match[
      "https?:\\/\\/[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)"
    ],
    "Bas URL regex."
  ]
opaque type URL = String :| URLRegex
object URL extends RefinedTypeOps[String, URLRegex, URL]:
  extension (url: URL)
    def getBytes: Array[Byte] =
      url.value.getBytes()

opaque type Usage = Int :| Positive0
object Usage extends RefinedTypeOps[Int, Positive0, Usage]:
  extension (u: Usage)
    def +(a: Int :| Positive0): Usage =
      Usage.assume(u.value + a)

type HashRegEx =
  DescribedAs[
    Match[
      "^([a-f0-9][a-f0-9])+$"
    ],
    "A Hash must only be bytes represented in hexadecimal."
  ]
opaque type Hash = String :| HashRegEx
object Hash extends RefinedTypeOps[String, HashRegEx, Hash]
