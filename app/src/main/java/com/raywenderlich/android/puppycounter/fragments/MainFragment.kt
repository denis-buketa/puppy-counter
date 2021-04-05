package com.raywenderlich.android.puppycounter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raywenderlich.android.puppycounter.R
import com.raywenderlich.android.puppycounter.fragments.viewmodels.MainViewModel
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

class MainFragment : Fragment() {

  companion object {

    const val TAG = "MainFragment"
  }

  private var dogCount: DogCount = DogCount()

  private lateinit var smallDogCountLabel: TextView
  private lateinit var middleDogCountLabel: TextView
  private lateinit var bigDogCountLabel: TextView

  private val viewModel: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    Timber.i("PuppyCounter - MainFragment - onCreate()")
    super.onCreate(savedInstanceState)

    savedInstanceState?.run {
      Timber.i("PuppyCounter - MainFragment - restoreState()")
      val savedDogCount: DogCount? = getParcelable(STATE_DOG_COUNT)
      dogCount = savedDogCount ?: DogCount()
    }
  }

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    Timber.i("PuppyCounter - MainFragment - onCreateView()")
    return inflater.inflate(R.layout.layout_main, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    Timber.i("PuppyCounter - MainFragment - onViewCreated()")
    super.onViewCreated(view, savedInstanceState)
    subscribeToViewModel()
    findViews(view)
    setupSmallDogViewsClickListeners(view)
    setupMiddleDogViewsClickListeners(view)
    setupBigDogViewsClickListeners(view)
  }

  private fun subscribeToViewModel() {
    viewModel.dogCount.observe(viewLifecycleOwner, { value ->
      dogCount = value
      renderDogCount(dogCount)
    })
  }

  override fun onStart() {
    Timber.i("PuppyCounter - MainFragment - onStart()")
    super.onStart()
  }

  override fun onResume() {
    Timber.i("PuppyCounter - MainFragment - onResume()")
    super.onResume()
    renderDogCount(dogCount)
  }

  override fun onPause() {
    Timber.i("PuppyCounter - MainFragment - onPause()")
    super.onPause()
  }

  override fun onStop() {
    Timber.i("PuppyCounter - MainFragment - onStop()")
    super.onStop()
  }

  override fun onDestroyView() {
    Timber.i("PuppyCounter - MainFragment - onDestroyView()")
    super.onDestroyView()
  }

  override fun onDestroy() {
    Timber.i("PuppyCounter - MainFragment - onDestroy()")
    super.onDestroy()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    Timber.i("PuppyCounter - MainFragment - onSaveInstanceState()")

    // Save the dog count state
    outState.run {
      putParcelable(STATE_DOG_COUNT, dogCount)
    }

    // Always call the superclass so it can save the view hierarchy state
    super.onSaveInstanceState(outState)
  }

  private fun findViews(view: View) {
    smallDogCountLabel = view.findViewById(R.id.smallDogCountLabel)
    middleDogCountLabel = view.findViewById(R.id.middleDogCountLabel)
    bigDogCountLabel = view.findViewById(R.id.bigDogCountLabel)
  }

  private fun setupSmallDogViewsClickListeners(view: View) {
    view.apply {
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
  }

  private fun setupMiddleDogViewsClickListeners(view: View) {
    view.apply {
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
  }

  private fun setupBigDogViewsClickListeners(view: View) {
    view.apply {
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
  }

  private fun updateDogCount(newDogCount: DogCount) {
    viewModel.setDogCount(newDogCount)
  }

  private fun renderDogCount(dogCount: DogCount) = with(dogCount) {
    smallDogCountLabel.text = smallDogCount.toString()
    middleDogCountLabel.text = middleDogCount.toString()
    bigDogCountLabel.text = bigDogCount.toString()
  }

  fun getDogCount(): DogCount = dogCount

  fun clearAllCounts() {
    updateDogCount(DogCount())
  }
}