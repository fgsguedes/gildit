package guedes

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.hardcoded.gildit.R

import kotlinx.android.synthetic.main.*

class TestActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main)
    setSupportActionBar(toolBar)

    hello.text = "World!"
  }
}