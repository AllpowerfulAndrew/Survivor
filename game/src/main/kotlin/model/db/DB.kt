package model.db

import model.db.entity.Location
import org.hibernate.SessionFactory
import org.hibernate.Transaction
import org.hibernate.cfg.Configuration

object DB {
    private var sessionFactory: SessionFactory? = null
    private var transaction: Transaction? = null

    private fun sessionFactory() = if (sessionFactory == null || sessionFactory!!.isClosed) {
        sessionFactory = Configuration().configure().buildSessionFactory()
        sessionFactory!!
    } else sessionFactory!!


    fun loadLocationFromDB(id: String): Location {
        sessionFactory().use {
            it.openSession().use {
                transaction = it.beginTransaction()
                val location = it.load(Location::class.java, id)!!
                transaction!!.commit()
                return location
            }
        }
    }
}