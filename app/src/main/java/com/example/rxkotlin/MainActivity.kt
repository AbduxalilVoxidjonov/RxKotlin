package com.example.rxkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.rxkotlin.databinding.ActivityMainBinding
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.FlowableEmitter
import io.reactivex.rxjava3.core.FlowableOnSubscribe
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fastflowable = Flowable.interval(100, TimeUnit.MILLISECONDS)
            .onBackpressureDrop{
                println("Dropped $it")
            }
        val createflowable = createFlowable()
        createflowable
            .buffer(5)
            .subscribe{
            binding.textV.text=it.toString()
        }



//        val b = createObservable()
//            .debounce(2000,TimeUnit.SECONDS)
//            .subscribeWith(object : Observer<String> {
//                override fun onSubscribe(d: Disposable) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onError(e: Throwable) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onComplete() {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onNext(t: String) {
//                    TODO("Not yet implemented")
//                }
//            })
//
//        binding.btn.setOnClickListener {
//            b.onComplete()
//        }
//

    }

//    fun createObservable(): Observable<String> {
//        return Observable.create(object : ObservableOnSubscribe<String> {
//            override fun subscribe(emitter: ObservableEmitter<String>) {
//                binding.editTx.addTextChangedListener {
//                    emitter.onNext(it.toString())
//                }
//            }
//
//        })
//    }
    fun createFlowable(): Flowable<String> {
        return Flowable.create(object : FlowableOnSubscribe<String> {
            override fun subscribe(emitter: FlowableEmitter<String>) {
                binding.editTx.addTextChangedListener {
                    emitter.onNext(it.toString())
                }
            }

        }, BackpressureStrategy.BUFFER)
    }
}