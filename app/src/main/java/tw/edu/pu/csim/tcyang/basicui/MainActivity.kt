package tw.edu.pu.csim.tcyang.basicui


import android.app.Activity
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tw.edu.pu.csim.tcyang.basicui.ui.theme.BasicUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BasicUITheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Main(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Main(modifier: Modifier = Modifier) {

    var Animals = listOf(R.drawable.animal0, R.drawable.animal1,
        R.drawable.animal2, R.drawable.animal3,
        R.drawable.animal4, R.drawable.animal5,
        R.drawable.animal6, R.drawable.animal7,
        R.drawable.animal8, R.drawable.animal9)

    var AnimalsName = arrayListOf("鴨子","企鵝",
        "青蛙","貓頭鷹","海豚", "牛", "無尾熊", "獅子", "狐狸", "小雞")
    var flag by remember { mutableStateOf(value="text") }

    // 取得當前的 Context
    val context = LocalContext.current

    // 使用 remember 儲存 MediaPlayer 實例
    var mper: MediaPlayer? by remember { mutableStateOf(null) }

    // **[修改 1/2]** 狀態變數：初始值改為列表中的第一個動物 (animal0 - 鴨子)
    var currentImageId by remember {
        mutableStateOf(Animals[0]) // R.drawable.animal0
    }


    // 使用 DisposableEffect 來管理 MediaPlayer 的生命週期
    // 當 Main Composable 離開組合時，會執行 onDispose 區塊
    DisposableEffect(Unit) { // Unit 作為 key 表示這個 effect 只會執行一次
        onDispose {
            // 釋放 MediaPlayer 資源，避免記憶體洩漏
            mper?.release()
            mper = null
        }
    }


    Column (
        modifier = modifier
            .fillMaxSize() // 1. 設定全螢幕（填滿父容器）
            .background(Color(0xFFE0BBE4)), // 4. 設定背景為淺紫色
        horizontalAlignment = Alignment.CenterHorizontally, // 2. 設定水平置中
        verticalArrangement = Arrangement.Top // 3. 設定垂直靠上
    ) {
        Text(text = stringResource(R.string.app_title),
            fontSize = 25.sp,
            color = Color.Blue,
            fontFamily = FontFamily(Font(R.font.kai))

        )

        Spacer(modifier = Modifier.size(10.dp))

        Text(text = stringResource(R.string.app_author),
            fontSize = 20.sp,
            color = Color(0xFF654321)
        )

        Spacer(modifier = Modifier.size(10.dp))

        Row {
            Image(
                painter = painterResource(id = R.drawable.android),
                contentDescription = "Android 圖示",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color.Yellow),
                alpha = 0.6f,
            )

            Image(
                painter = painterResource(id = R.drawable.compose),
                contentDescription = "Compose icon",
                modifier = Modifier.size(100.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.firebase),
                contentDescription = "Firebase icon",
                modifier = Modifier.size(100.dp)
            )

        }

        Spacer(modifier = Modifier.size(10.dp))

        LazyRow {
            items(51) { index ->
                Text(text = "$index:")
                Text(text = AnimalsName[index % 10])

                Image(
                    painter = painterResource(id = Animals[index % 10]),
                    contentDescription = "可愛動物",
                    modifier = Modifier.size(60.dp)
                )

            }



        }
        Spacer(modifier = Modifier.size(10.dp))

        Button(
            onClick = {
                if(flag=="text"){
                    flag="abc"
                }
                else{
                    flag="text"
                }

                /*Toast.makeText(
                    context,
                    text="Compose 按鈕被點了"
                    duration=Toast.LEN
                )*/
            }
        ){
            Text(text="按鈕測試")
        }

        Text(text=flag)

        Spacer(modifier = Modifier.size(10.dp))
        Row{
            Button(
                onClick = {
                    mper = MediaPlayer.create(context, R.raw.tcyang) //設定音樂
                    mper?.start()
                },
                modifier = Modifier
                    .fillMaxWidth(0.33f)
                    .fillMaxHeight(0.8f),
                colors = buttonColors(Color.Green)
            ) {
                Text(text = "歡迎", color = Color.Blue)
                Text(text = "修課", color = Color.Red)
                Image(painterResource(id = R.drawable.teacher),
                    contentDescription ="teacher icon")
            }

            Spacer(modifier = Modifier.size(10.dp))

            Button(onClick = {
                mper?.release()  //釋放資源
                mper = null // 清除舊引用
                mper = MediaPlayer.create(context, R.raw.fly) //設定音樂
                mper?.start()
            },
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.4f),
                colors = buttonColors(Color.Blue)

            ) {
                Text(text = "展翅飛翔", color = Color.White)
                Image(
                    painterResource(id = R.drawable.fly),
                    contentDescription ="fly icon")


            }

            Spacer(modifier = Modifier.size(10.dp))

            Button(onClick = {
                val activity=context as? Activity
                activity?.finish()


            },
                // 設定按鈕顏色為亮藍色
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BFFF)),

                //形狀：將元素的每個角落「切掉」一個直角
                shape = CutCornerShape(10),

                //藍色框線
                border = BorderStroke(1.dp, Color.Blue),

                //陰影
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)

            ) {
                Text(text = "結束App")
            }

        } // End of Row for 3 buttons


        // 圖片切換按鈕區塊
        Spacer(modifier = Modifier.size(20.dp)) // 增加一些間距

        Button(
            onClick = {
                // 在 animal0 (鴨子) 和 animal1 (企鵝) 之間切換
                currentImageId = if (currentImageId == Animals[0]) {
                    // 如果當前是 animal0 (鴨子)，就切換到 animal1 (企鵝)
                    Animals[1]
                } else {
                    // 否則，切換回 animal0 (鴨子)
                    Animals[0]
                }
            },
            // 設定按鈕外觀為透明，這樣就只會看到圖片本身
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp), // 移除陰影
            contentPadding = ButtonDefaults.ContentPadding,
            // ****** 圖片按鈕尺寸已從 100.dp 增加到 150.dp ******
            modifier = Modifier.size(150.dp)
            // **********************************************
        ) {
            // 顯示根據狀態切換的圖片
            Image(
                painter = painterResource(id = currentImageId),
                contentDescription = "可切換的動物圖示",
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(text = "⬆️ 點擊上方動物圖示可切換圖片 (鴨子/企鵝) ⬆️", fontSize = 16.sp)
        // 圖片切換按鈕區塊結束


    }
}