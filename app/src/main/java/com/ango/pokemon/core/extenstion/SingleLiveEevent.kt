package com.ango.pokemon.core.extenstion

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/*
    SingleLiveEvent class used for emitting one time
    event to one observe at a time, and each update correspond to an emit
*/
class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val notEmitted = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        //check if there are more than one observer
        if (hasActiveObservers()) {
            Log.w(TAG, "only one observer will be notified of changes.")
        }

        // Observe the internal MutableLiveData
        super.observe(owner, object : Observer<T> {
            override fun onChanged(value: T?) {
                //if value not emitted, emit value and update the flag to stop update observers
                if (notEmitted.compareAndSet(true, false)) {
                    observer.onChanged(value)
                }
            }
        })
    }

    @MainThread
    override fun setValue(t: T?) {
        notEmitted.set(true)
        super.setValue(t)
    }


    companion object {
        private val TAG = "SingleLiveEvent"
    }
}