package com.raywenderlich.android.puppycounter

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

    fun create(dogCount: DogCount): ShareFragment = ShareFragment().apply {
      arguments = Bundle().apply {
        putParcelable(ARGUMENT_DOG_COUNT, dogCount)
      }
    }
  }

  private val viewModel: ShareViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    readArguments()
  }

  private fun readArguments() {
    val dogCountArgument: DogCount? = arguments?.getParcelable(ARGUMENT_DOG_COUNT)
    requireNotNull(dogCountArgument)
    with(dogCountArgument) {
      viewModel.smallDogCount = smallDogCount
      viewModel.middleDogCount = middleDogCount
      viewModel.bigDogCount = bigDogCount
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_share, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    populateDogCountInfo(view)
    setOnShareBtnClickListener(view)
    openDialogIfNeeded()
  }

  private fun populateDogCountInfo(view: View) {
    view.apply {
      findViewById<TextView>(R.id.smallDogStats).text = "Small: ${viewModel.smallDogCount}"
      findViewById<TextView>(R.id.middleDogStats).text = "Middle: ${viewModel.middleDogCount}"
      findViewById<TextView>(R.id.bigDogStats).text = "Big: ${viewModel.bigDogCount}"
    }
  }

  private fun setOnShareBtnClickListener(view: View) {
    view.findViewById<Button>(R.id.shareBtn).setOnClickListener {
      openShareDialog()
    }
  }

  private fun openShareDialog() {
    AlertDialog.Builder(requireContext())
      .setTitle("Are you sure you want to share these stats?")
      .setPositiveButton("Yes") { dialog, _ ->
        dialog.dismiss()
        Toast.makeText(requireContext(), "Puppies Happy :]", Toast.LENGTH_SHORT).show()
      }
      .setNegativeButton("No") { dialog, _ ->
        dialog.dismiss()
        Toast.makeText(requireContext(), "Puppies Sad :[", Toast.LENGTH_SHORT).show()
      }
      .setOnDismissListener {
        viewModel.dialogOpen = false
      }
      .show()
    viewModel.dialogOpen = true
  }

  private fun openDialogIfNeeded() {
    if (viewModel.dialogOpen) {
      openShareDialog()
    }
  }
}