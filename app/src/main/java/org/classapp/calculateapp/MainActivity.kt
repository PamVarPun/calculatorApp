package org.classapp.calculateapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import org.classapp.calculateapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var lastNumeric = false
    var stateError = false
    var lastDot = false

    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onALlClearClick(view: View) {

        binding.tvData.text = ""
        binding.tvResult.text = ""
        stateError = false
        lastDot = false
        lastNumeric = false
        binding.tvResult.visibility = View.GONE
    }

    fun onEqualClick(view: View) {

        OnEqual()
        binding.tvData.text = binding.tvResult.text.toString()

    }

    fun onDigitClick(view: View) {

        if(stateError){

            binding.tvData.text = (view as Button).text
            stateError = false
        }else{

            binding.tvData.append((view as Button).text)

        }
            lastNumeric= true
            OnEqual()
    }

    fun onOperatorClick(view: View) {

        if(!stateError && lastNumeric){

            binding.tvData.append((view as Button).text)
            lastDot = false
            lastNumeric = false
            OnEqual()
        }

    }

    fun onBackClick(view: View) {

        binding.tvData.text = binding.tvData.text.toString().dropLast(1)
        try{

            val lastChar = binding.tvData.text.toString().last()
            if(lastChar.isDigit()){

                OnEqual()
            }

        }catch (e : Exception){

            binding.tvResult.text = ""
            binding.tvResult.visibility = View.GONE
            Log.e("last char error", e.toString())
        }

    }

    fun onClearClick(view: View) {

        binding.tvData.text = ""
        lastNumeric = false


    }


    fun OnEqual(){
        if(lastNumeric && !stateError){

            val txt = binding.tvData.text.toString()
            expression = ExpressionBuilder(txt).build()

            try{

                val result = expression.evaluate()

                binding.tvResult.visibility = View.VISIBLE

                binding.tvResult.text = "=" + result.toString()

            }catch (ex: Exception){
                Log.e("evaluate error" ,ex.toString())
                binding.tvResult.text = "Error"
                stateError = true
                lastNumeric = false
            }



        }
    }
}