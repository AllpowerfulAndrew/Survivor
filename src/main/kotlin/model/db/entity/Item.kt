package model.db.entity


import javax.persistence.*
import javax.persistence.FetchType.EAGER

@Entity
@Table(name = "items")
class Item {

    @Id
    @Column(name = "placement")
    var placement: String? = null

    @Column(name = "key")
    var key: String? = null

    @Column(name = "weight")
    var weight: Double = .0

    @Column(name = "takeable")
    var takeable: Boolean = false

    @Column(name = "containable")
    var containable: Boolean = false

    @Column(name = "name")
    var name: String? = null

    @OneToMany(fetch = EAGER, mappedBy = "itemId")
    var keys: Set<Key>? = null

    @OneToMany(fetch = EAGER, mappedBy = "itemId")
    var calls: Set<Call>? = null

    @OneToMany(fetch = EAGER, mappedBy = "itemId")
    var descriptions: Set<Description>? = null

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "location_id")
    var locationId: Location? = null

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "section_id")
    var sectionId: Section? = null

    override fun toString() =
            "Item(placement=$placement, " +
                    "key=$key, " +
                    "name=$name, " +
                    "calls=$calls, " +
                    "descriptions=$descriptions)"
}
