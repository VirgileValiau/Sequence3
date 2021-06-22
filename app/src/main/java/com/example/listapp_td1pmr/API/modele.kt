import com.google.gson.annotations.SerializedName

data class Token_API(
    @SerializedName("hash")
    val hash: String
)

data class ReponseUsers(
        @SerializedName("users")
        val users:MutableList<User_API>
)

data class User_API(
        @SerializedName("id")
        val id:Int,
        @SerializedName("pseudo")
        val pseudo:String
)

data class ListsUser_API(
        @SerializedName("lists")
        val lists:MutableList<ToDoList>
)

data class ToDoList(
        @SerializedName("label")
        val label:String,
        @SerializedName("items")
        val items:MutableList<Item>,
        @SerializedName("id")
        val id:Int
)

data class Item_Response(
        @SerializedName("items")
        val items:MutableList<Item>
)

data class Item(
        @SerializedName("label")
        val label:String,
        @SerializedName("id")
        val id:Int,
        @SerializedName("checked")
        val checked:Int,
        @SerializedName("url")
        val url:String
)
