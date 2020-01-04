package lengereau.wilfred.recyclerview

import com.squareup.moshi.Json

data class Task(
    @field:Json(name = "id")
    val id: String? = null,
    @field:Json(name = "description")
    val description: String = "description",
    @field:Json(name = "title")
    val title: String
)