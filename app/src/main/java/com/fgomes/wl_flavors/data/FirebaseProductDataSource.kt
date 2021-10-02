package com.fgomes.wl_flavors.data

import android.net.Uri
import com.fgomes.wl_flavors.BuildConfig
import com.fgomes.wl_flavors.domain.model.Product
import com.fgomes.wl_flavors.util.COLLECTION_PRODUCTS
import com.fgomes.wl_flavors.util.COLLECTION_ROOT
import com.fgomes.wl_flavors.util.STORAGE_IMAGE
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.*
import kotlin.coroutines.suspendCoroutine

class FirebaseProductDataSource(
    firebaseFirestore: FirebaseFirestore,
    firebaseStorage: FirebaseStorage
): ProductDataSource {

    private val documentReference = firebaseFirestore
        .document("$COLLECTION_ROOT/${BuildConfig.FIREBASE_FLAVOR_COLLECTION}/")

    private val storageReference = firebaseStorage.reference

    override suspend fun getProducts(): List<Product> {
        return suspendCoroutine { continuation ->
            val productsReference = documentReference.collection(COLLECTION_PRODUCTS)
            productsReference.get().addOnSuccessListener { documents ->
                val products = mutableListOf<Product>()
                for (document in documents) {
                    document.toObject(Product::class.java).run{
                        products.add(this)
                    }
                }

                continuation.resumeWith(Result.success(products))
            }

            productsReference.get().addOnFailureListener { exception ->
                continuation.resumeWith(Result.failure(exception))
            }
        }

    }

    override suspend fun uploadProductImage(imageUri: Uri): String {
        return suspendCoroutine { continuation ->
            val randomKey = UUID.randomUUID()
            //image/"car"/key
            val childReference = storageReference.child(
                "$STORAGE_IMAGE/${BuildConfig.FIREBASE_FLAVOR_COLLECTION}/$randomKey"
            )

            childReference.putFile(imageUri)
                .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                    val path = uri.toString()
                    continuation.resumeWith(Result.success(path))

                    }
                }.addOnFailureListener { exception ->
                    continuation.resumeWith(Result.failure(exception))

                }
        }
    }

    override suspend fun createProduct(product: Product): Product {
        return suspendCoroutine { continuation ->
            documentReference
                .collection(COLLECTION_PRODUCTS)
                .document(System.currentTimeMillis().toString())
                .set(product)
                .addOnSuccessListener {
                    continuation.resumeWith(Result.success(product))
                }
                .addOnFailureListener{ exception ->
                    continuation.resumeWith(Result.failure(exception))
                }
        }
    }
}