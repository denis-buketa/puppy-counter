package com.raywenderlich.android.puppycounter

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    Timber.i("onCreate() - instance: $this")
    setContentView(R.layout.activity_main)

    // Only add fragment to this activity if activity is not being recreated (e.g. config change)
    if (savedInstanceState == null) {
      supportFragmentManager.commit {
        setReorderingAllowed(true)
        add(R.id.fragmentContainerView, MainFragment(), MainFragment.TAG)
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

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    val inflater: MenuInflater = menuInflater
    inflater.inflate(R.menu.menu_activity_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.shareAction -> {
        startShareActivity()
        true
      }
      R.id.clearAction -> {
        notifyClearAll()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun startShareActivity() {
    val intent = ShareActivity.createIntent(this, getDogCount())
    startActivity(intent)
  }

  private fun getDogCount(): DogCount {
    val mainFragment = supportFragmentManager.findFragmentByTag(MainFragment.TAG) as? MainFragment
    requireNotNull(mainFragment)
    return mainFragment.getDogCount()
  }

  private fun notifyClearAll() {
    val mainFragment = supportFragmentManager.findFragmentByTag(MainFragment.TAG) as? MainFragment
    requireNotNull(mainFragment)
    mainFragment.clearAllCounts()
  }
}