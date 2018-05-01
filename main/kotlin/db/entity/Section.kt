package db.entity

import javax.persistence.*
import javax.persistence.FetchType.EAGER

@Entity
@Table(name = "sections")
class Section {
    @Id
    @Column(name = "id")
    var id: String? = null

    @OneToMany(fetch = EAGER, mappedBy = "section")
    var descriptions: Set<Description>? = null

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "location_id")
    var location: Location? = null
}