package entity

import javax.persistence.*
import javax.persistence.FetchType.EAGER

@Entity
@Table(name = "sections")
class Section {

    @Id
    @Column(name = "key")
    var key: String? = null

    @Column(name = "location")
    var location: String? = null

    @Column(name = "name")
    var name: String? = null

    @OneToMany(fetch = EAGER, mappedBy = "sectionId")
    var descriptions: Set<Description>? = null

    @OneToMany(fetch = EAGER, mappedBy = "sectionId")
    var directions: Set<Direction>? = null

    @OneToMany(fetch = EAGER, mappedBy = "sectionId")
    var items: Set<Item>? = null

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "location_id")
    var locationId: Location? = null

    override fun toString()=
            "Section(location=$location, " +
                    "key=$key, " +
                    "name=$name, " +
                    "descriptions=$descriptions, " +
                    "directions=$directions, " +
                    "items=$items)"
}