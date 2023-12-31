package com.umc.chamma.ui.using

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.TranslateAnimation
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.umc.chamma.R
import com.umc.chamma.config.BaseActivityVB
import com.umc.chamma.databinding.ActivityGameBinding

class GameActivity : BaseActivityVB<ActivityGameBinding>(ActivityGameBinding::inflate){

    private lateinit var moles : Array<ImageButton>
    private lateinit var levels : Array<Int>

    private val range = (0..11)

    // 구멍 비어있는지 체크. 0이면 비어있고, 1이면 차있는상태
    private var holestate : Array<Int> = arrayOf(0,0,0,0,0,0,0,0,0,0,0,0)

    // 두더지 상태. 검은색 : 1 / 갈색 : 0 / 토끼 : 2
    private var molestate : Array<Int> = arrayOf(0,0,0,0,0,0,0,0,0,0,0,0)

    // Pause 상태인지 체크
    private var pausestate = false

    // 레벨업한 상태인지 체크
    private var levelupstate = false

    private var time = 100
    private var score : Int = 0
    private var life = 5

    private var sleeptime : Long = 0
    private var progresstime : Long = 0

    private var mediaPlayer: MediaPlayer? = null
    private var musicstate = true


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFullScreen()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        mediaPlayer = MediaPlayer.create(this, R.raw.bgm)

        val pausebtn = binding.btnIngamePause
        val musicbtn = binding.btnIngameMusic
        val hammerbtn = binding.btnIngameHammer
        val scoretxt = binding.tvIngmaeScore
        val lifetxt = binding.tvIngmaeLife
        val level = binding.ivIngameLevel
        val soundPool = SoundPool.Builder().build()

        val hitsound = soundPool.load(this, R.raw.hitsound,1)
        val levelupsound = soundPool.load(this, R.raw.levelupsound, 1)



        // level drawable array
        levels = arrayOf(R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.four, R.drawable.five,
            R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.nine, R.drawable.ten)

        // 두더지 array

        moles = arrayOf(binding.btnIngameMole1,
            binding.btnIngameMole2, binding.btnIngameMole3, binding.btnIngameMole4, binding.btnIngameMole5,
            binding.btnIngameMole6, binding.btnIngameMole7, binding.btnIngameMole8, binding.btnIngameMole9,
            binding.btnIngameMole10, binding.btnIngameMole11, binding.btnIngameMole12)

        for(i in 0 until 12){

            // 모든 두더지가 안보이는상태로 초기화
            moles[i].visibility= View.INVISIBLE

            moles[i].setOnTouchListener{ v: View, event: MotionEvent ->
                // 클릭시 효과음
                soundPool.play(hitsound, 1.0f, 1.0f, 0,0,1.0f)

                // 두번클릭 안되게 방지
                moles[i].isClickable = false

                if(molestate[i] == 2){          // 토끼 클릭했을 경우
                    Glide.with(this)
                        .load(R.drawable.hitrabbit)
                        .into(moles[i])
                    time -= 5
                }
                else if(molestate[i] == 1){      // 검은두더지 클릭했을 경우
                    Glide.with(this)
                        .load(R.drawable.hitblackmole)
                        .into(moles[i])
                    life--          // 라이프 감소
                    Log.d("ttt", "$life")
                    lifetxt.text = life.toString()
                }else{                      // 갈색두더지 클릭했을 경우
                    Glide.with(this)
                        .load(R.drawable.hitmole)
                        .into(moles[i])


                    // 레벨 구간별 점수 증가폭 증가. 점수 증가
                    when(score){
                        in (0..19) -> score++
                        in (20..50) -> score += 2
                        in (51..450) -> score += 3
                    }
                    scoretxt.text = score.toString()


                    // 타임이 100 아래일때, 타임 증가
                    if(time < 100){
                        time += 10
                    }


                    // 10/ 20/ 40/ 80/ 160/ 320 점마다 레벨 상승
                    // & 두더지 속도 상승
                    // & 시간 감소속도 상승

                    when(score){
                        10 -> {         // 10점일때 : 레벨2
                            levelupstate = true

                            Glide.with(this)
                                .load(levels[1])
                                .into(level)
                            soundPool.play(levelupsound, 1.0f, 1.0f, 0,0,1.0f)

                            progresstime += 40
                        }
                        20 ->{           // 20점일때 : 레벨3
                            levelupstate = true

                            Glide.with(this)
                                .load(levels[2])
                                .into(level)
                            soundPool.play(levelupsound, 1.0f, 1.0f, 0,0,1.0f)

                            sleeptime += 100
                            progresstime += 40

                        }
                        40 ->{          // 40점일때 : 레벨4
                            levelupstate = true

                            Glide.with(this)
                                .load(levels[3])
                                .into(level)
                            soundPool.play(levelupsound, 1.0f, 1.0f, 0,0,1.0f)

                            sleeptime += 200
                            progresstime += 20

                        }
                        80 ->{          // 80점일때 : 레벨5
                            levelupstate = true

                            Glide.with(this)
                                .load(levels[4])
                                .into(level)
                            soundPool.play(levelupsound, 1.0f, 1.0f, 0,0,1.0f)

                            sleeptime += 200
                            progresstime += 20

                        }
                        160 ->{         // 160점일때 : 레벨6
                            levelupstate = true

                            Glide.with(this)
                                .load(levels[5])
                                .into(level)
                            soundPool.play(levelupsound, 1.0f, 1.0f, 0,0,1.0f)

                            sleeptime += 200

                        }
                        320 ->{         // 320점일때 : 레벨7
                            levelupstate = true

                            Glide.with(this)
                                .load(levels[6])
                                .into(level)
                            soundPool.play(levelupsound, 1.0f, 1.0f, 0,0,1.0f)

                            progresstime += 20

                        }
                    }
                }

                false
            }

        }


        pausebtn.setOnClickListener {
            val intent = Intent(this, PauseActivity::class.java)
            startActivity(intent)
        }

        musicbtn.setOnClickListener {
            if(musicstate){
                Glide.with(this)
                    .load(R.drawable.soundoff)
                    .into(musicbtn)
                mediaPlayer?.pause()
                musicstate = false
            }else{
                Glide.with(this)
                    .load(R.drawable.soundon)
                    .into(musicbtn)
                mediaPlayer?.start()

                musicstate =  true
            }

        }

        hammerbtn.setOnClickListener {

        }

    }

    private fun setFullScreen(){
        window.apply {
            statusBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    override fun onResume() {
        super.onResume()
        pausestate = false
        gameThread()
        musicstate = true
        mediaPlayer?.start()
    }

    override fun onPause() {
        super.onPause()
        pausestate = true

        mediaPlayer?.pause()
    }


    private fun gameThread(){
        val levelupmodal = binding.ivIngameLevelupmodal
        levelupmodal.isVisible = false

        Thread{
            moleThreadDark()
            moleThreadLong()
            moleThreadMedium()
            moleThreadShort()
            rabbitThread()
            timeThread()
            levelupThread(levelupmodal)

            // 게임 종료조건 해당시 while문 탈출
            while(life > 0 && time > 0){

            }

            // 게임 Over
            val intent = Intent(this, GameoverActivity::class.java)
                .putExtra("SCORE",score)
            startActivity(intent)

        }.start()

    }


    // 느린 두더지 스레드
    private fun moleThreadLong(){
        Thread{

            while(!pausestate){

                // 랜덤 인덱스 생성
                var index = range.random()

                // 구멍 이미 차있을때, 비어있는 구멍 찾는 로직
                while(holestate[index] == 1){
                    index = range.random()
                }

                // 해당 인덱스에 해당하는 두더지상태 변경
                molestate[index] = 0

                // 해당 구멍 차있음 표시
                holestate[index] = 1

                // 두더지 등장
                runOnUiThread {
                    Glide.with(this)
                        .load(R.drawable.mole)
                        .into(moles[index])
                    val anim = TranslateAnimation(0f,0f,30f,0f)
                    anim.duration = 50
                    moles[index].animation = anim
                    moles[index].visibility = View.VISIBLE
                }


                Thread.sleep(1500 - sleeptime)

                // 두더지 퇴장
                runOnUiThread {
                    val anim = TranslateAnimation(0f,0f,0f,30f)
                    anim.duration = 50
                    moles[index].animation = anim
                    moles[index].visibility = View.INVISIBLE
                    moles[index].isClickable = true
                }

                holestate[index] = 0

                Thread.sleep(1500 - sleeptime)

            }
        }.start()
    }

    // 중간 두더지 스레드
    private fun moleThreadMedium(){
        Thread{

            while(!pausestate){
                var index = range.random()

                while(holestate[index] == 1){
                    index = range.random()
                }

                molestate[index] = 0
                holestate[index] = 1

                runOnUiThread {
                    Glide.with(this)
                        .load(R.drawable.mole)
                        .into(moles[index])
                    val anim = TranslateAnimation(0f,0f,30f,0f)
                    anim.duration = 50
                    moles[index].animation = anim
                    moles[index].visibility = View.VISIBLE
                }


                Thread.sleep(1000 - sleeptime)

                runOnUiThread {
                    val anim = TranslateAnimation(0f,0f,0f,30f)
                    anim.duration = 50
                    moles[index].animation = anim
                    moles[index].visibility = View.INVISIBLE
                    moles[index].isClickable = true
                }
                holestate[index] = 0

                Thread.sleep(1000 - sleeptime)
            }
        }.start()
    }


    // 빠른 두더지 스레드
    private fun moleThreadShort(){
        Thread{

            while(!pausestate){
                var index = range.random()

                while(holestate[index] == 1){
                    index = range.random()
                }

                molestate[index] = 0
                holestate[index] = 1

                runOnUiThread {
                    Glide.with(this)
                        .load(R.drawable.mole)
                        .into(moles[index])
                    val anim = TranslateAnimation(0f,0f,30f,0f)
                    anim.duration = 50
                    moles[index].animation = anim
                    moles[index].visibility = View.VISIBLE
                }


                Thread.sleep(800 - sleeptime)

                runOnUiThread {
                    val anim = TranslateAnimation(0f,0f,0f,30f)
                    anim.duration = 50
                    moles[index].animation = anim
                    moles[index].visibility = View.INVISIBLE
                    moles[index].isClickable = true
                }
                holestate[index] = 0


                Thread.sleep(800 - sleeptime)
            }
        }.start()
    }


    // 검은 두더지 스레드
    private fun moleThreadDark(){
        Thread{

            while(!pausestate){
                var index = range.random()

                while(holestate[index] == 1){
                    index = range.random()
                }

                molestate[index] = 1
                holestate[index] = 1

                runOnUiThread {
                    Glide.with(this)
                        .load(R.drawable.blackmole)
                        .into(moles[index])
                    val anim = TranslateAnimation(0f,0f,30f,0f)
                    anim.duration = 50
                    moles[index].animation = anim
                    moles[index].visibility = View.VISIBLE
                }


                Thread.sleep(2000- sleeptime)

                runOnUiThread {
                    val anim = TranslateAnimation(0f,0f,0f,30f)
                    anim.duration = 50
                    moles[index].animation = anim
                    moles[index].visibility = View.INVISIBLE
                    moles[index].isClickable = true
                }

                holestate[index] = 0

                Thread.sleep(2000- sleeptime)
            }
        }.start()
    }

    // 토기 스레드

    private fun rabbitThread(){
        Thread{

            while(!pausestate){

                // 랜덤 인덱스 생성
                var index = range.random()

                while(holestate[index] == 1){
                    index = range.random()
                }


                // 해당 인덱스에 해당하는 몬스터 상태 변경
                molestate[index] = 2
                holestate[index] = 1

                // 두더지 등장
                runOnUiThread {
                    Glide.with(this)
                        .load(R.drawable.rabbit)
                        .into(moles[index])

                    val anim = TranslateAnimation(0f,0f,30f,0f)
                    anim.duration = 50
                    moles[index].animation = anim
                    moles[index].visibility = View.VISIBLE
                }


                Thread.sleep(1500 - sleeptime)

                // 두더지 퇴장
                runOnUiThread {
                    val anim = TranslateAnimation(0f,0f,0f,30f)
                    anim.duration = 50
                    moles[index].animation = anim
                    moles[index].visibility = View.INVISIBLE
                    moles[index].isClickable = true
                }

                holestate[index] = 0

                Thread.sleep(1500 - sleeptime)

            }
        }.start()
    }


    // 시간 감소 스레드
    private fun timeThread(){
        val timebar = binding.pbIngameTimebar

        Thread{
            while(time > 0 && !pausestate){
                time -= 1

                runOnUiThread {
                    timebar.progress = time
                }

                Thread.sleep(150 - progresstime)
            }

        }.start()
    }


    // 레벨업 모달 스레드

    private fun levelupThread(levelupmodal : ImageView){

        Thread{

            while(true){
                if(levelupstate){

                    runOnUiThread {

                        val anim = TranslateAnimation(0f,0f,200f,0f)
                        anim.duration = 400
                        anim.fillAfter = true
                        levelupmodal.animation = anim
                        levelupmodal.visibility = View.VISIBLE

                    }

                    Thread.sleep(1000)

                    runOnUiThread {
                        val anim = TranslateAnimation(0f,0f,0f,levelupmodal.width.toFloat())
                        anim.duration = 400
                        levelupmodal.animation = anim
                        levelupmodal.visibility = View.GONE
                    }

                    levelupstate = false

                }
            }
        }.start()
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}