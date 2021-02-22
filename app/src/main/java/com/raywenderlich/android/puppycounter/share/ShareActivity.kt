package com.raywenderlich.android.puppycounter.share

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.raywenderlich.android.puppycounter.model.DogCount
import com.raywenderlich.android.puppycounter.R
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

private const val EXTRA_DOG_COUNT = "ShareActivity_extra_dog_count"

class ShareActivity : AppCompatActivity() {

  companion object {

    fun createIntent(context: Context, dogCount: DogCount) =
      Intent(context, ShareActivity::class.java).apply {
        putExtra(EXTRA_DOG_COUNT, dogCount)
      }
  }

  private lateinit var dogCount: DogCount

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Timber.i("onCreate() - instance: $this")
    setContentView(R.layout.activity_share)
    readExtras()
    if (savedInstanceState == null) {
      supportFragmentManager.commit {
        setReorderingAllowed(true)
        add(R.id.fragmentContainerView, ShareFragment.create(dogCount))
      }
    }
  }

  override fun onStart() {
    super.onStart()
    Timber.i("onStart() - instance: $this")
  }

  override fun onResume() {
    super.onResume()
    Timber.i("onResume() - instance: $this")
  }

  override fun onPause() {
    Timber.i("onPause() - instance: $this")
    super.onPause()
  }

  override fun onStop() {
    Timber.i("onStop() - instance: $this")
    super.onStop()
  }

  override fun onDestroy() {
    Timber.i("onDestroy() - instance: $this")
    super.onDestroy()
  }

  private fun readExtras() {
    val dogCountExtra: DogCount? = intent.extras?.getParcelable(EXTRA_DOG_COUNT)
    requireNotNull(dogCountExtra)
    dogCount = dogCountExtra
  }

}