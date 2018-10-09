# Circle Chart
Custom Circle Chart with your text, max value and progress value
![DynamicSeekbar](https://raw.githubusercontent.com/minhnn2607/CircleChart/master/screenshot.png)

## Setup
Add dependency to your __build.gradle__

```groovy		
maven {
    url "https://dl.bintray.com/minhnn2607/maven"
}
```	
```groovy		
compile 'vn.nms.circle_chart:CircleChart:1.0'
```	
## Usage
Add __DynamicSeekBarView__ to your layout

```groovy	
    <vn.nms.circle_chart.CircleChart
        android:id="@+id/circleChart"
        android:layout_width="200dp"
        android:layout_height="200dp"
        app:sc_arrowColor="@color/colorPrimaryDark"
        app:sc_arrowHeadLength="3dp"                             //Head length of arrow progress
        app:sc_arrowLength="12dp"                                //Tail length of arrow progress
        app:sc_arrowWidth="7dp"                                  //Width of arrow progress
        
        app:sc_botFontColor="@color/colorPrimaryDark"            //Bottom text color
        app:sc_botFontSize="14sp"                                //Bottom text font size
        app:sc_botText="Daily Spend Time"                        //Bottom text
        
        app:sc_dashColor="@color/gray_bold"                      //Inactive dash color
        app:sc_dashProgressColor="@android:color/black"          //Active dash color
        app:sc_dashLength="10dp"                                 //Length of dash
        app:sc_dashSpaceAngle="3"                                //Space between dash
        app:sc_dashWidthAngle="1"                                //Size of dash
        
        app:sc_duration="1000"                                   //Duration of animation
        
        app:sc_endAngle="45"                                     //Circle end angel
        app:sc_startAngle="-225"                                 //Circle start angel
        
        app:sc_marginBot="15dp"                                  //Target text margin bot
        app:sc_targetFontColor="@android:color/holo_red_dark"    //Target text color
        app:sc_targetFontSize="14sp"                             //Target font size
        app:sc_targetText="Target: 100 hours"                    //Target text
        
        app:sc_topFontColor="@android:color/black"               //Top text color
        app:sc_topFontSize="13sp"                                //Top font size
        app:sc_topText="Total Spent Time"                        //Top text
        app:sc_marginTop="10dp"                                  //Top text martin top
        
        app:sc_totalFontColor="@color/colorPrimaryDark"          //Total text color  
        app:sc_totalFontSize="50sp"                              //Total font size
        /> 
```

## Develop By
Minh Nguyen
        
## License
```
Copyright 2017 Minh Nguyen

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

          
        
