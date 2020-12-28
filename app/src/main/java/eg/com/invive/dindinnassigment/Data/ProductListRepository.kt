/*
 * Copyright (c) 2020 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package eg.com.invive.dindinnassigment.Data

import eg.com.invive.dindinnassigment.Model.CategoryInfo
import eg.com.invive.dindinnassigment.Model.HomeResponse
import eg.com.invive.dindinnassigment.Model.ProductInfo
import io.reactivex.Observable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers


class ProductListRepository {

    init {
        RxJavaPlugins.setErrorHandler { e: Throwable? -> }
    }

    private val sliderImages = mutableListOf<String>()
    private val orders = mutableListOf<ProductInfo>()
    private val products = mutableListOf<ProductInfo>(
        ProductInfo(0,0,"MARGHERITA","Go Back to where it all began with the classic cheese and tomato base",60,"https://storage.eu.content-cdn.io/cdn-cgi/image/%7Bwidth%7D,%7Bheight%7D,quality=75,format=auto,fit=cover,g=top/am-resources/c3877a59-69f7-40fa-bb17-ae5b9ac37732/Images/ProductImages/Large/margherita-min1.png")
    , ProductInfo(1,0,"CLASSIC PEPPERONI","One of our all time specialties. A meaty feast of Pepperoni, Mushroom , Black Olives, mozzarella cheese and tomato sauce",50,"https://storage.eu.content-cdn.io/cdn-cgi/image/%7Bwidth%7D,%7Bheight%7D,quality=75,format=auto,fit=cover,g=top/am-resources/c3877a59-69f7-40fa-bb17-ae5b9ac37732/Images/ProductImages/Large/classic_pepperoni_showcase-min1.png")
    , ProductInfo(2,0,"CHICKEN SUPREME","The ultimate mix of Chicken together with Mushrooms, Red Onions, Green Peppers",70,"https://storage.eu.content-cdn.io/cdn-cgi/image/%7Bwidth%7D,%7Bheight%7D,quality=75,format=auto,fit=cover,g=top/am-resources/c3877a59-69f7-40fa-bb17-ae5b9ac37732/Images/ProductImages/Large/chicken%20supreme.png")
    , ProductInfo(3,0,"SPICY CHICKEN RANCH","Grilled chicken, fresh tomatoes, mushrooms and green Jalapeño peppers with a drizzle o",75,"https://storage.eu.content-cdn.io/cdn-cgi/image/%7Bwidth%7D,%7Bheight%7D,quality=75,format=auto,fit=cover,g=top/am-resources/c3877a59-69f7-40fa-bb17-ae5b9ac37732/Images/ProductImages/Large/Tuna.png")
        , ProductInfo(4,1,"Makizushi","Long, thin rolls typically featuring just one ingredient like a strip of fresh tuna, cucumber, or pickled daikon are called hosomaki",50,"https://gurunavi.com/en/japanfoodie/article/types_of_sushi/img/01_Sushi.jpg")
    , ProductInfo(5,1,"Nigiri"," A simple coating of marinade and garnishes such as spring onions, shaved onion, or chives may also be added.",70,"https://gurunavi.com/en/japanfoodie/article/types_of_sushi/img/05_Sushi.jpg")
    , ProductInfo(6,2," Avocado and papaya","Sweet papaya replaces gula melaka in this twist to the popular avocado food stall drink. As a superfood",20,"https://hw-media.herworld.com/public/2018/07/story/avocado_and_papaya.jpg")
    , ProductInfo(7,2,"Bittergourd, apple and lemon","Pairing bittergourd with sweet apples and tart lemons helps improve the flavour of bittergourd",30,"https://hw-media.herworld.com/public/2018/07/story/bittergourd.jpg")
    )
    private val categories = mutableListOf<CategoryInfo>()

    fun  getHomeResponse()=Observable.fromCallable<HomeResponse>{
        Thread.sleep(3000)
        categories.addAll(listOf(
            CategoryInfo(0,"Pizza")
            , CategoryInfo(1,"Sushi")
            , CategoryInfo(2,"Drinks")
        ))
        sliderImages.addAll(listOf(
            "https://storage.eu.content-cdn.io/am-resources/b7d7f7ad-cafb-4c0a-afb8-1a5341e7212b/Images/ProductImages/Source/Super%20Star%20Large%20Offer%20small%20EN.jpg",
            "https://storage.eu.content-cdn.io/am-resources/b7d7f7ad-cafb-4c0a-afb8-1a5341e7212b/Images/ProductImages/Source/Saytara_480x400%20mobile%20banner-EN.jpg",
            "https://promolkwebsite.blob.core.windows.net/promotions/promo.lk-promo-f41184422141410a9c890b9e6c88bb60.jpg"
        ))
        HomeResponse(sliderImages,categories)
    }.subscribeOn(Schedulers.io())
    fun getProductListByCategoryID(id:Int) = Observable.fromCallable<List<ProductInfo>> {
        Thread.sleep(3000)
        products.filter { it.category==id }
  }.subscribeOn(Schedulers.io())

    fun addToOrder(productInfo: ProductInfo)= Observable.fromCallable {
        orders.add(productInfo)
        orders
    }.subscribeOn(Schedulers.io())



    fun removeFromOrder(productInfo: ProductInfo)=Observable.fromCallable {
        orders.remove(productInfo)
        orders
    }.subscribeOn(Schedulers.io())


    fun getUserOrder(): Observable<List<ProductInfo>> {
        return Observable.fromCallable {
            orders
        }
    }

}