package de.syntax.androidabschluss.data.model


data class Notes(

    var id: String = "",
    var title: String = "",
    var subTitle: String = "",
    var dateTime: String = "",
    var noteText: String = "",
    var color: Int = 0

    ) {
    override fun toString(): String {
        return "$title : $dateTime"

    }
}