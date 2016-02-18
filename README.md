# CanAnimation
使用android属性动画写的一个库，可组建动画队列，可实现同时、顺序、重复播放等。
 ![](./pic/CanRefresh.gif)  

##添加依赖
```JAVA
compile 'com.canyinghao:cananimation:1.0.0'
```

## 使用方式 
**1. 使用方法**  
CanAnimation是一个动画库，可按队列播放动画。CanObjectAnimator类是使用ObjectAnimator写的动画，使用时需要传一些初始值，不然会有问题，比如移动到（500，500），你下一个移动动画要传（500，500）才能连上上一个动画，不方便。所以推荐使用CanValueAnimator类。CanValueAnimator类继承自ValueAnimator，通过反射设置属性值，而且重写start()方法，在start()方法中获取view的初始值，不用传如初始值，使用相当方便。

CanObjectAnimator使用代码：
```JAVA

  CanObjectAnimator builder = CanObjectAnimator.on(view);


            animationSequence(builder.moveTo(center.x, center.y, 0),

                    animationTogether(builder.moveTo(center.x,newPos.x,center.y, newPos.y, 2),
                            builder.rotateTo(360, 2),
                            builder.color(Color.BLUE, Color.RED, 2),
                            builder.colorText(Color.WHITE, Color.BLUE, 2)


                    ),
                    animationTogether(builder.scaleTo(0.5f, 0.5f,2),
                            builder.rotateTo(720,2),
                            animationSequence(
                                    builder.color(Color.RED, Color.BLACK, 0.3f),
                                    builder.color(Color.BLACK, Color.RED, 0.3f),
                                    builder.color(Color.RED, Color.GREEN, 0.3f),
                                    builder.color(Color.GREEN, Color.RED, 0.3f),
                                    builder.color(Color.RED, Color.YELLOW, 0.3f),
                                    builder.color(Color.YELLOW, Color.RED, 0.3f),
                                    builder.color(Color.RED, Color.BLUE, 0.3f)
                            )
                    ),

                    animationTogether(
                            builder.moveTo(newPos.x,center.x, newPos.y,center.y, 2),
                            builder.scaleTo(0.5f,2, 0.5f,2, 2),
                            builder.rotateTo(360, 2),
                            builder.color(Color.BLUE, Color.GRAY, 2),
                            builder.colorText(Color.BLUE, Color.WHITE, 2)

                    ),


                    builder.run(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar.make(toolbar, "动画播放完毕", Snackbar.LENGTH_SHORT).show();
                        }
                    })

            ).start();

``` 
CanValueAnimator使用代码：
```JAVA

 CanValueAnimator.Builder builder = CanValueAnimator.Builder.on(view);


            animationSequence(builder.moveTo(center.x, center.y, 0),

                    animationTogether(builder.moveTo(newPos.x, newPos.y, 2),
                            builder.rotateTo(360, 2),
                            builder.color(Color.BLUE, Color.RED, 2),
                            builder.colorText(Color.WHITE, Color.BLUE, 2)


                    ),
                    animationTogether(builder.scaleTo(0.5f, 0.5f,2),
                            builder.rotateTo(720,2),
                            animationSequence(
                                    builder.color(Color.RED, Color.BLACK, 0.3f),
                                    builder.color(Color.BLACK, Color.RED, 0.3f),
                                    builder.color(Color.RED, Color.GREEN, 0.3f),
                                    builder.color(Color.GREEN, Color.RED, 0.3f),
                                    builder.color(Color.RED, Color.YELLOW, 0.3f),
                                    builder.color(Color.YELLOW, Color.RED, 0.3f),
                                    builder.color(Color.RED, Color.BLUE, 0.3f)
                            )
                    ),

                    animationTogether(
                            builder.moveTo(center.x, center.y, 2),
                            builder.scaleTo(2, 2, 2),
                            builder.rotateTo(360, 2),
                            builder.color(Color.BLUE, Color.GRAY, 2),
                            builder.colorText(Color.BLUE, Color.WHITE, 2)

                    ),


                    builder.run(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar.make(toolbar,"动画播放完毕",Snackbar.LENGTH_SHORT).show();
                        }
                    })
                    

            ).start();

``` 
**2. 翻转动画**  
翻转动画通过使用ViewFlipper和Camera等实现。
使用代码：
```JAVA
public class FlipperAnimeActivity extends AppCompatActivity {


    @Bind(R.id.vf)
    ViewFlipper vf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flipper);
        ButterKnife.bind(this);


        vf.postDelayed(new Runnable() {
            @Override
            public void run() {
                CanFlipAnimation.flipForever(vf, CanFlipAnimation.FlipDirection.LEFT_RIGHT,1000,null,0);
            }
        },1000);
    }
}

``` 




### 开发者

![](https://avatars3.githubusercontent.com/u/12572840?v=3&s=460) 

canyinghao: <canyinghao@hotmail.com>  


### License

    Copyright 2016 canyinghao

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
