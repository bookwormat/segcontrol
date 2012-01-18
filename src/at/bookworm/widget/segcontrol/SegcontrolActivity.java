/*
 * Copyright 2011 Benjamin Ferrari
 * http://bookworm.at
 * https://github.com/bookwormat/segcontrol
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

package at.bookworm.widget.segcontrol;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioGroup;

/** @author benjamin ferrari */
public class SegcontrolActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        RadioGroup group1 = (RadioGroup) this.findViewById(R.id.buttongroup1);
        group1.check(R.id.option1);

        RadioGroup group2 = (RadioGroup) this.findViewById(R.id.buttongroup2);
        group2.check(R.id.option2);
    }
}