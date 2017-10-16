/*
 This file is part of Privacy Friendly Dice Game.

 Privacy Friendly PDice Game is free software:
 you can redistribute it and/or modify it under the terms of the
 GNU General Public License as published by the Free Software Foundation,
 either version 3 of the License, or any later version.

 Privacy Friendly Dice Game is distributed in the hope
 that it will be useful, but WITHOUT ANY WARRANTY; without even
 the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 See the GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with Privacy Friendly Dice Game. If not, see <http://www.gnu.org/licenses/>.
 */

package org.secuso.privacyfriendlydicegame;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * @author Karola Marky
 */

public class HelpFragment extends PreferenceFragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.help);
    }

}