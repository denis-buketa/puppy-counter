package com.raywenderlich.android.puppycounter.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.raywenderlich.android.puppycounter.R
import com.raywenderlich.android.puppycounter.fragments.viewmodels.ShareViewModel
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

private const val ARGUMENT_DOG_COUNT = "ShareFragment_extra_dog_count"

class ShareFragment : Fragment() {

  companion object {

    const val TAG = "ShareFragment"

    fun create(dogCount: DogCount): ShareFragment = ShareFragment().apply {
      arguments = Bundle().apply {
        putParcelable(ARGUMENT_DOG_COUNT, dogCount)
      }
    }
  }

  private val viewModel: ShareViewModel by viewModels()

  private lateinit var smallDogStatsLabel: TextView
  private lateinit var middleDogStatsLabel: TextView
  private lateinit var bigDogStatsLabel: TextView

  override fun onCreate(savedInstanceState: Bundle?) {
    Timber.i("PuppyCounter - ShareFragment - onCreate()")
    super.onCreate(savedInstanceState)
    readArguments()
  }

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View? {
    Timber.i("PuppyCounter - ShareFragment - onCreateView()")
    return inflater.inflate(R.layout.layout_share, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    Timber.i("PuppyCounter - ShareFragment - onViewCreated()")
    super.onViewCreated(view, savedInstanceState)
    subscribeToViewModel()
    findViews(view)
    setOnShareBtnClickListener(view)
    openDialogIfNeeded()
  }

  override fun onStart() {
    Timber.i("PuppyCounter - ShareFragment - onStart()")
    super.onStart()
  }

  override fun onResume() {
    Timber.i("PuppyCounter - ShareFragment - onResume()")
    super.onResume()
  }

  override fun onPause() {
    Timber.i("PuppyCounter - ShareFragment - onPause()")
    super.onPause()
  }

  override fun onStop() {
    Timber.i("PuppyCounter - ShareFragment - onStop()")
    super.onStop()
  }

  override fun onDestroyView() {
    Timber.i("PuppyCounter - ShareFragment - onDestroyView()")
    super.onDestroyView()
  }

  override fun onDestroy() {
    Timber.i("PuppyCounter - ShareFragment - onDestroy()")
    super.onDestroy()
  }

  private fun readArguments() {
    val dogCount: DogCount? = arguments?.getParcelable(ARGUMENT_DOG_COUNT)
    requireNotNull(dogCount)
    viewModel.setDogCount(dogCount)
  }

  private fun subscribeToViewModel() {
    viewModel.dogCount.observe(viewLifecycleOwner, { dogCount ->
      renderDogCount(dogCount)
    })
  }

  private fun findViews(view: View) {
    smallDogStatsLabel = view.findViewById(R.id.smallDogStats)
    middleDogStatsLabel = view.findViewById(R.id.middleDogStats)
    bigDogStatsLabel = view.findViewById(R.id.bigDogStats)
  }

  private fun setOnShareBtnClickListener(view: View) {
    view.findViewById<Button>(R.id.shareBtn).setOnClickListener {
      openShareDialog()
    }
  }

  private fun renderDogCount(dogCount: DogCount) = with(dogCount) {
    smallDogStatsLabel.text = getString(R.string.small_dog_stats, smallDogCount.toString())
    middleDogStatsLabel.text = getString(R.string.middle_dog_stats, middleDogCount.toString())
    bigDogStatsLabel.text = getString(R.string.big_dog_stats, bigDogCount.toString())
  }

  private fun openDialogIfNeeded() {
    if (viewModel.dialogOpen) {
      openShareDialog()
    }
  }

  private fun openShareDialog() {
    AlertDialog.Builder(requireContext())
        .setTitle(R.string.share_dialog_title)
        .setPositiveButton(R.string.share_dialog_yes) { dialog, _ ->
          dialog.dismiss()
          Toast.makeText(requireContext(), R.string.puppies_happy, Toast.LENGTH_SHORT).show()
        }
        .setNegativeButton(R.string.share_dialog_no) { dialog, _ ->
          dialog.dismiss()
          Toast.makeText(requireContext(), R.string.puppies_sad, Toast.LENGTH_SHORT).show()
        }
        .setOnDismissListener {
          viewModel.dialogOpen = false
        }
        .show()
    viewModel.dialogOpen = true
  }
}