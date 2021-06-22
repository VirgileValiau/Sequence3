import retrofit2.http.*


interface ApiService {

    @POST
    suspend fun getToken(@Url url:String): Token_API

    @GET
    suspend fun getUsers(@Url url:String,@Header("hash")token:String): ReponseUsers

    @POST
    suspend fun createNewList(@Url url:String,@Header("hash")token:String)

    @POST
    suspend fun createNewItem(@Url url:String,@Header("hash")token:String)

    @GET
    suspend fun getListes(@Url url:String,@Header("hash")token:String): ListsUser_API

    @GET
    suspend fun getItems(@Url url:String,@Header("hash")token:String): Item_Response

    @PUT
    suspend fun checkItem(@Url url:String,@Header("hash")token:String)

}