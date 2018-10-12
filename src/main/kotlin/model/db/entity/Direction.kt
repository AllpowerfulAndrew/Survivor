package model.db.entity

import javax.persistence.*

@Entity
@Table(name = "directions")
class Direction() {

    constructor(side: String?, location: String?, section: String?) : this(){
        this.side = side
        this.location = location
        this.section = section
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    var id: Int? = null

    @Column(name = "side")
    var side: String? = null

    @Column(name = "location")
    var location: String? = null

    @Column(name = "section")
    var section: String? = null

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "section_id")
    var sectionId: Section? = null

    override fun toString()= "Direction(side=$side, location=$location, section=$section)"
}