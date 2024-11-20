package com.ash.traveally.dao

import android.net.Uri
import com.ash.traveally.models.Message
import com.ash.traveally.utils.NetworkResult
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class FirebaseDao @Inject constructor(
    private val chatCollection: CollectionReference,
    private val storage: FirebaseStorage
) {

    fun getAllMessages(userId1: Long, userId2: Long) = callbackFlow {
        val listener = chatCollection
            .where(Filter.or(Filter.equalTo("senderId", userId1), Filter.equalTo("senderId", userId2)))
            .where(Filter.or(Filter.equalTo("receiverId", userId1), Filter.equalTo("receiverId", userId2)))
            .orderBy("time", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, e ->
            if (e != null) {
                close(e)
            }
            if (snapshot != null) {
                val messages = snapshot.toObjects(Message::class.java)
                trySend(messages)
            }
        }
        awaitClose {
            listener.remove()
        }
    }

    suspend fun addMessage(message: String, userId1: Long, userId2: Long) {
        val doc = chatCollection.document()
        doc.set(Message(id = doc.id, senderId = userId1, receiverId = userId2, message = message)).await()
    }

    suspend fun uploadImage(uri: Uri): NetworkResult<String> {
        try {
            val uniqueName = UUID.randomUUID().toString()
            val userRef = storage.reference.child("userImages/${uniqueName}")
            val downloadUrl = userRef.putFile(uri).await().storage.downloadUrl.await()
            if (downloadUrl != null) {
                return NetworkResult.Success(downloadUrl.toString())
            }
        } catch (e: Exception) {
            return NetworkResult.Error(e.message)
        }
        return NetworkResult.Error("Something went wrong")
    }
}