package fr.eirb.cassis.users

import fr.eirb.cassis.CAS

enum User:

  def name: CAS

  def isAdmin: Boolean =
    this match
      case Admin(_) => true
      case _        => false

  case Normal(name: CAS) extends User
  case Admin(name: CAS) extends User

  override def equals(that: Any): Boolean =
    that match
      case user: User if this.isAdmin == user.isAdmin => name.equals(user.name)
      case _                                          => false
