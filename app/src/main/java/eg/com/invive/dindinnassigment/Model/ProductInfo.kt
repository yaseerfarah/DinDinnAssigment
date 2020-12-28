package eg.com.invive.dindinnassigment.Model

import eg.com.invive.dindinnassigment.Interface.POJOBase

data class ProductInfo(
    override val id:Int
    , val category:Int
    , val name:String
    , val details:String
    , val price:Int
    , val imageUrl:String
                       ):POJOBase