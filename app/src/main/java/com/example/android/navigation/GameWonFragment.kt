/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.navigation

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.android.navigation.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_game_won, container, false)

        // Fetch DATA from GameFragment.kt using SafeArgs.
        val args = GameWonFragmentArgs.fromBundle(requireArguments())

        // Display DATA using Toast
        Toast.makeText(context, "NumCorrect: ${args.numCorrect}, NumQuestions: ${args.numQuestions}", Toast.LENGTH_LONG).show()


        // Button: Next Match
        binding.nextMatchButton.setOnClickListener { view: View ->
            view.findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
            // view.findNavController().navigate(R.id.action_gameWonFragment_to_gameFragment)
        }

        // We are informing the system that we will be using option menu for this fragment
        setHasOptionsMenu(true)

        return binding.root
    }


    // Configuring our Options Menu through XML file
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        // Inflate our menu so it can be shown in the view
        super.onCreateOptionsMenu(menu, inflater)

        // Take the UI Design from @res/menu/winner_menu.xml file
        inflater.inflate(R.menu.winner_menu, menu)

        //
        if (getShareIntent().resolveActivity(requireActivity().packageManager) == null) {
            menu.findItem(R.id.share).isVisible = false
        }

    }

    // What to do when the user will click on the menu item?
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        // If the menu's id = share then initalize shareSuccess() function
        when (item.itemId) {
            R.id.share -> shareSuccess()
        }

        return super.onOptionsItemSelected(item)
    }

    // This function will be executed when the user will click on the share icon in options menu
    private fun shareSuccess() {
        startActivity(getShareIntent())
    }

    // This function will be called though shareSuccess() function
    // Markup for our Share Intent
    private fun getShareIntent(): Intent {

        // It will save DATA i.e. Total Questions and Correct Answers retrieved from GameFragment
        val args = GameWonFragmentArgs.fromBundle(requireArguments())

        // Intent Type: Share
        val shareIntent = Intent(Intent.ACTION_SEND)

        // Defining structure of our Intent message with args previously saved from GameFragment
        shareIntent.setType("text/plain")
                .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_success_text, args.numCorrect, args.numQuestions))

        // Submit our intent
        return shareIntent
    }

}

