package com.raywenderlich.android.puppycounter.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.raywenderlich.android.puppycounter.R
import com.raywenderlich.android.puppycounter.model.DogCount
import timber.log.Timber

/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom
 * the Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify,
 * merge, publish, distribute, sublicense, create a derivative work,
 * and/or sell copies of the Software in any work that is designed,
 * intended, or marketed for pedagogical or instructional purposes
 * related to programming, coding, application development, or
 * information technology. Permission for such use, copying,
 * modification, merger, publication, distribution, sublicensing,
 * creation of derivative works, or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks
 * that are released under various Open-Source licenses. Use of
 * those libraries and frameworks are governed by their own
 * individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

private const val STATE_DOG_COUNT = "state_dog_count"

class MainActivity : AppCompatActivity() {

  var dogCount: DogCount = DogCount()

  private lateinit var smallDogCountLabel: TextView
  private lateinit var middleDogCountLabel: TextView
  private lateinit var bigDogCountLabel: TextView

  override fun onStart() {
    Timber.i("PuppyCounter - MainActivity - onStart()")
    super.onStart()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    Timber.i("PuppyCounter - MainActivity - onCreate()")
    super.onCreate(savedInstanceState)
    setContentView(R.layout.layout_main)

    findViews()

    setupSmallDogViewsClickListeners()
    setupMiddleDogViewsClickListeners()
    setupBigDogViewsClickListeners()
  }

  override fun onResume() {
    Timber.i("PuppyCounter - MainActivity - onResume()")
    super.onResume()
    renderDogCount(dogCount)
  }

  override fun onPause() {
    Timber.i("PuppyCounter - MainActivity - onPause()")
    super.onPause()
  }

  override fun onStop() {
    Timber.i("PuppyCounter - MainActivity - onStop()")
    super.onStop()
  }

  override fun onDestroy() {
    Timber.i("PuppyCounter - MainActivity - onDestroy()")
    super.onDestroy()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    val inflater: MenuInflater = menuInflater
    inflater.inflate(R.menu.menu_activity_main, menu)
    return true
  }

  private fun findViews() {
    smallDogCountLabel = findViewById(R.id.smallDogCountLabel)
    middleDogCountLabel = findViewById(R.id.middleDogCountLabel)
    bigDogCountLabel = findViewById(R.id.bigDogCountLabel)
  }

  private fun setupSmallDogViewsClickListeners() {
    findViewById<CardView>(R.id.smallDog).setOnClickListener {
      updateDogCount(dogCount.incrementSmallDogCount())
    }
    findViewById<ImageView>(R.id.smallDogMinusBtn).setOnClickListener {
      updateDogCount(dogCount.decrementSmallDogCount())
    }
    findViewById<ImageView>(R.id.smallDogPlusBtn).setOnClickListener {
      updateDogCount(dogCount.incrementSmallDogCount())
    }
  }

  private fun setupMiddleDogViewsClickListeners() {
    findViewById<CardView>(R.id.middleDog).setOnClickListener {
      updateDogCount(dogCount.incrementMiddleDogCount())
    }
    findViewById<ImageView>(R.id.middleDogMinusBtn).setOnClickListener {
      updateDogCount(dogCount.decrementMiddleDogCount())
    }
    findViewById<ImageView>(R.id.middleDogPlusBtn).setOnClickListener {
      updateDogCount(dogCount.incrementMiddleDogCount())
    }
  }

  private fun setupBigDogViewsClickListeners() {
    findViewById<CardView>(R.id.bigDog).setOnClickListener {
      updateDogCount(dogCount.incrementBigDogCount())
    }
    findViewById<ImageView>(R.id.bigDogMinusBtn).setOnClickListener {
      updateDogCount(dogCount.decrementBigDogCount())
    }
    findViewById<ImageView>(R.id.bigDogPlusBtn).setOnClickListener {
      updateDogCount(dogCount.incrementBigDogCount())
    }
  }

  private fun updateDogCount(newDogCount: DogCount) {
    dogCount = newDogCount
    renderDogCount(dogCount)
  }

  private fun renderDogCount(dogCount: DogCount) {
    smallDogCountLabel.text = dogCount.smallDogCount.toString()
    middleDogCountLabel.text = dogCount.middleDogCount.toString()
    bigDogCountLabel.text = dogCount.bigDogCount.toString()
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.shareAction -> {
        showShareScreen()
        true
      }
      R.id.clearAction -> {
        clearAll()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun showShareScreen() {
    Timber.i("PuppyCounter - MainActivity - start ShareActivity Intent")
    val intent = ShareActivity.createIntent(this)
    startActivity(intent)
  }

  private fun clearAll() {
    updateDogCount(DogCount())
  }
}