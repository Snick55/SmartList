package com.snick55.smartlist.lists.presentation

import androidx.lifecycle.ViewModel
import com.snick55.smartlist.core.FirebaseDatabaseProvider
import com.snick55.smartlist.core.FirebaseProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddListViewModel @Inject constructor(
    private val firebaseProvider: FirebaseProvider,
    private val firebaseDatabaseProvider: FirebaseDatabaseProvider,
): ViewModel() {




    fun createList(name:String){
        val uuid = UUID.randomUUID()
        firebaseDatabaseProvider.provideDBRef().child("newList").child(firebaseProvider.provideAuth().currentUser!!.uid).child("$uuid").child("name").setValue(name)
        firebaseDatabaseProvider.provideDBRef().child("newList").child(firebaseProvider.provideAuth().currentUser!!.uid).child("$uuid").child("date").setValue(System.currentTimeMillis())


    }

}