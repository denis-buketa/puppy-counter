package com.raywenderlich.android.puppycounter.main

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
import com.raywenderlich.android.puppycounter.share.ShareActivity
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

  var smallDogCount: Int = 0
    set(value) {
      field = validateValue(value)
    }

  var middleDogCount: Int = 0
    set(value) {
      field = validateValue(value)
    }

  var bigDogCount: Int = 0
    set(value) {
      field = validateValue(value)
    }

  private lateinit var smallDogCountLabel: TextView
  private lateinit var middleDogCountLabel: TextView
  private lateinit var bigDogCountLabel: TextView

  override fun onStart() {
    super.onStart()
    Timber.i("PuppyCounter - MainActivity - onStart()")
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Timber.i("PuppyCounter - MainActivity - onCreate()")
    setContentView(R.layout.fragment_main)

    findViews()

    setupSmallDogViewsClickListeners()
    setupMiddleDogViewsClickListeners()
    setupBigDogViewsClickListeners()
  }

  override fun onResume() {
    super.onResume()
    Timber.i("PuppyCounter - MainActivity - onResume()")
    renderViewModelState()
  }

  override fun onPause() {
    super.onPause()
    Timber.i("PuppyCounter - MainActivity - onPause()")
  }

  override fun onStop() {
    super.onStop()
    Timber.i("PuppyCounter - MainActivity - onStop()")
  }

  override fun onDestroy() {
    super.onDestroy()
    Timber.i("PuppyCounter - MainActivity - onDestroy()")
  }

  override fun onSaveInstanceState(outState: Bundle) {
    Timber.i("PuppyCounter - MainActivity - onSaveInstanceState()")

    // Save the dog count state
    outState.run {
      val dogCount = DogCount(smallDogCount, middleDogCount, bigDogCount)
      putParcelable(STATE_DOG_COUNT, dogCount)
    }

    // Always call the superclass so it can save the view hierarchy state
    super.onSaveInstanceState(outState)
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    Timber.i("PuppyCounter - MainActivity - onRestoreInstanceState()")

    // Always call the superclass so it can restore the view hierarchy
    super.onRestoreInstanceState(savedInstanceState)

    with(savedInstanceState) {
      val dogCount: DogCount? = getParcelable(STATE_DOG_COUNT)
      dogCount?.let {
        smallDogCount = it.smallDogCount
        middleDogCount = it.middleDogCount
        bigDogCount = it.bigDogCount
      }
    }
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
      smallDogCount += 1
      renderViewModelState()
    }
    findViewById<ImageView>(R.id.smallDogMinusBtn).setOnClickListener {
      smallDogCount -= 1
      renderViewModelState()
    }
    findViewById<ImageView>(R.id.smallDogPlusBtn).setOnClickListener {
      smallDogCount += 1
      renderViewModelState()
    }
  }

  private fun setupMiddleDogViewsClickListeners() {
    findViewById<CardView>(R.id.middleDog).setOnClickListener {
      middleDogCount += 1
      renderViewModelState()
    }
    findViewById<ImageView>(R.id.middleDogMinusBtn).setOnClickListener {
      middleDogCount -= 1
      renderViewModelState()
    }
    findViewById<ImageView>(R.id.middleDogPlusBtn).setOnClickListener {
      middleDogCount += 1
      renderViewModelState()
    }
  }

  private fun setupBigDogViewsClickListeners() {
    findViewById<CardView>(R.id.bigDog).setOnClickListener {
      bigDogCount += 1
      renderViewModelState()
    }
    findViewById<ImageView>(R.id.bigDogMinusBtn).setOnClickListener {
      bigDogCount -= 1
      renderViewModelState()
    }
    findViewById<ImageView>(R.id.bigDogPlusBtn).setOnClickListener {
      bigDogCount += 1
      renderViewModelState()
    }
  }

  private fun renderViewModelState() {
    smallDogCountLabel.text = smallDogCount.toString()
    middleDogCountLabel.text = middleDogCount.toString()
    bigDogCountLabel.text = bigDogCount.toString()
  }

  /**
   * Ensures negative values can't be set to dogs count.
   */
  private fun validateValue(value: Int): Int = if (value < 0) 0 else value

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.shareAction -> {
        startShareActivity()
        true
      }
      R.id.clearAction -> {
        clearAll()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun startShareActivity() {
    val intent = ShareActivity.createIntent(this)

    Timber.i("PuppyCounter - MainActivity - create ShareActivity Intent")
    // 1. Create DogCount instance
    val dogCount = DogCount(smallDogCount, middleDogCount, bigDogCount)
    // 2. Add DogCount state to intent
    intent.putExtra(ShareActivity.EXTRA_DOG_COUNT, dogCount)

    startActivity(intent)
  }

  private fun clearAll() {
    smallDogCount = 0
    middleDogCount = 0
    bigDogCount = 0
    renderViewModelState()
  }
}