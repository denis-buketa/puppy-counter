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

class MainActivity : AppCompatActivity() {

  var smallDogCount: Int = 0
    set(value) {
      field = if (value < 0) {
        0
      } else {
        value
      }
    }

  var middleDogCount: Int = 0
    set(value) {
      field = if (value < 0) {
        0
      } else {
        value
      }
    }

  var bigDogCount: Int = 0
    set(value) {
      field = if (value < 0) {
        0
      } else {
        value
      }
    }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.fragment_main)

    setupSmallDogViewsClickListeners()
    setupMiddleDogViewsClickListeners()
    setupBigDogViewsClickListeners()
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    val inflater: MenuInflater = menuInflater
    inflater.inflate(R.menu.menu_activity_main, menu)
    return true
  }

  private fun renderViewModelState() {
    updateSmallDogLabel()
    updateMiddleDogLabel()
    updateBigDogLabel()
  }

  private fun setupSmallDogViewsClickListeners() {
    findViewById<CardView>(R.id.smallDog).setOnClickListener {
      smallDogCount += 1
      updateSmallDogLabel()
    }
    findViewById<ImageView>(R.id.smallDogMinusBtn).setOnClickListener {
      smallDogCount -= 1
      updateSmallDogLabel()
    }
    findViewById<ImageView>(R.id.smallDogPlusBtn).setOnClickListener {
      smallDogCount += 1
      updateSmallDogLabel()
    }
  }

  private fun updateSmallDogLabel() {
    findViewById<TextView>(R.id.smallDogCountLabel)?.text = smallDogCount.toString()
  }

  private fun setupMiddleDogViewsClickListeners() {
    findViewById<CardView>(R.id.middleDog).setOnClickListener {
      middleDogCount += 1
      updateMiddleDogLabel()
    }
    findViewById<ImageView>(R.id.middleDogMinusBtn).setOnClickListener {
      middleDogCount -= 1
      updateMiddleDogLabel()
    }
    findViewById<ImageView>(R.id.middleDogPlusBtn).setOnClickListener {
      middleDogCount += 1
      updateMiddleDogLabel()
    }
  }

  private fun updateMiddleDogLabel() {
    findViewById<TextView>(R.id.middleDogCountLabel)?.text = middleDogCount.toString()
  }

  private fun setupBigDogViewsClickListeners() {
    findViewById<CardView>(R.id.bigDog).setOnClickListener {
      bigDogCount += 1
      updateBigDogLabel()
    }
    findViewById<ImageView>(R.id.bigDogMinusBtn).setOnClickListener {
      bigDogCount -= 1
      updateBigDogLabel()
    }
    findViewById<ImageView>(R.id.bigDogPlusBtn).setOnClickListener {
      bigDogCount += 1
      updateBigDogLabel()
    }
  }

  private fun updateBigDogLabel() {
    findViewById<TextView>(R.id.bigDogCountLabel)?.text = bigDogCount.toString()
  }

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
    startActivity(intent)
  }

  private fun clearAll() {
    smallDogCount = 0
    middleDogCount = 0
    bigDogCount = 0
    renderViewModelState()
  }
}