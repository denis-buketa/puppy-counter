package com.raywenderlich.android.puppycounter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

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

private const val PREFS_NAME = "dog_count"
private const val PREFS_KEY_SMALL_DOG = "small_dog_count"
private const val PREFS_KEY_MIDDLE_DOG = "middle_dog_count"
private const val PREFS_KEY_BIG_DOG = "big_dog_count"

class MainFragment : Fragment() {

  companion object {
    const val TAG = "MainFragment"
  }

  private val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    readDogCountFromPrefs()
  }

  private fun readDogCountFromPrefs() {
    val sharedPref = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    viewModel.apply {
      smallDogCount = sharedPref.getInt(PREFS_KEY_SMALL_DOG, 0)
      middleDogCount = sharedPref.getInt(PREFS_KEY_MIDDLE_DOG, 0)
      bigDogCount = sharedPref.getInt(PREFS_KEY_BIG_DOG, 0)
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_main, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    renderViewModelState()
    setupSmallDogViewsClickListeners()
    setupMiddleDogViewsClickListeners()
    setupBigDogViewsClickListeners()
  }

  private fun renderViewModelState() {
    updateSmallDogLabel()
    updateMiddleDogLabel()
    updateBigDogLabel()
  }

  private fun setupSmallDogViewsClickListeners() {
    view?.apply {
      findViewById<CardView>(R.id.smallDog).setOnClickListener {
        viewModel.smallDogCount += 1
        updateSmallDogLabel()
      }
      findViewById<ImageView>(R.id.smallDogMinusBtn).setOnClickListener {
        viewModel.smallDogCount -= 1
        updateSmallDogLabel()
      }
      findViewById<ImageView>(R.id.smallDogPlusBtn).setOnClickListener {
        viewModel.smallDogCount += 1
        updateSmallDogLabel()
      }
    }
  }

  private fun updateSmallDogLabel() {
    view?.findViewById<TextView>(R.id.smallDogCountLabel)?.text = viewModel.smallDogCount.toString()
  }

  private fun setupMiddleDogViewsClickListeners() {
    view?.apply {
      findViewById<CardView>(R.id.middleDog).setOnClickListener {
        viewModel.middleDogCount += 1
        updateMiddleDogLabel()
      }
      findViewById<ImageView>(R.id.middleDogMinusBtn).setOnClickListener {
        viewModel.middleDogCount -= 1
        updateMiddleDogLabel()
      }
      findViewById<ImageView>(R.id.middleDogPlusBtn).setOnClickListener {
        viewModel.middleDogCount += 1
        updateMiddleDogLabel()
      }
    }
  }

  private fun updateMiddleDogLabel() {
    view?.findViewById<TextView>(R.id.middleDogCountLabel)?.text =
      viewModel.middleDogCount.toString()
  }

  private fun setupBigDogViewsClickListeners() {
    view?.apply {
      findViewById<CardView>(R.id.bigDog).setOnClickListener {
        viewModel.bigDogCount += 1
        updateBigDogLabel()
      }
      findViewById<ImageView>(R.id.bigDogMinusBtn).setOnClickListener {
        viewModel.bigDogCount -= 1
        updateBigDogLabel()
      }
      findViewById<ImageView>(R.id.bigDogPlusBtn).setOnClickListener {
        viewModel.bigDogCount += 1
        updateBigDogLabel()
      }
    }
  }

  private fun updateBigDogLabel() {
    view?.findViewById<TextView>(R.id.bigDogCountLabel)?.text = viewModel.bigDogCount.toString()
  }

  fun getDogCount(): DogCount = viewModel.run {
    DogCount(smallDogCount, middleDogCount, bigDogCount)
  }

  fun clearAllCounts() {
    viewModel.smallDogCount = 0
    viewModel.middleDogCount= 0
    viewModel.bigDogCount = 0
    renderViewModelState()
  }

  override fun onStop() {
    saveDogCountToPrefs()
    super.onStop()
  }

  private fun saveDogCountToPrefs() {
    val dogCount = getDogCount()
    val sharedPref = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
      putInt(PREFS_KEY_SMALL_DOG, dogCount.smallDogCount)
      putInt(PREFS_KEY_MIDDLE_DOG, dogCount.middleDogCount)
      putInt(PREFS_KEY_BIG_DOG, dogCount.bigDogCount)
      apply()
    }
  }
}