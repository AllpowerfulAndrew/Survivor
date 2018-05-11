package model.db.entity

import javax.persistence.*
import javax.persistence.FetchType.EAGER

@Entity
@Table(name = "locations")
class Location() {

    constructor(key: String): this() {
        this.key = key
    }

    @Id
    @Column(name = "key")
    var key: String? = null

    @Column(name = "name")
    var name: String? = null

    @OneToMany(fetch = EAGER, mappedBy = "locationId")
    var sections: Set<Section>? = null

    @OneToMany(fetch = EAGER, mappedBy = "locationId")
    var items: Set<Item>? = null

    override fun toString()=
            "Location(name=$name, " +
                    "key=$key, " +
                    "sections=$sections, " +
                    "items=$items)"
}