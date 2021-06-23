package com.example.playmusicmain

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.playmusicmain.databinding.ActivityMainBinding
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class MainActivity : AppCompatActivity() {

    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMainBinding? = null
    //매번 null 체크를 할 필요 없이 편의성을 위해 바인딩 변수 재선언
    private val binding get() = mBinding!!

    private var mediaPlayer: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        // 자동 생성된 뷰 바인딩 클래스에서의 iflate 라는 메서드를 활용하여
        // 액티비티에서 사용할 바인딩 클래스의 인스턴스 생성
        mBinding = ActivityMainBinding.inflate(layoutInflater)

        // getRoot 메서드로 레이아웃 내부의 최상위 위치 뷰의
        // 인스턴스를 활용하여 생성된 뷰를 액티비티에 표시 합니다.
        setContentView(binding.root)

        //뷰 id는 파스칼 케이스 + 카멜 케이스의 네이밍 규칙 적용 tv_program -> tvProgram


        //musicplay - 음악 재생 버튼
        binding.btnPlay.setOnClickListener {
            if (binding.btnPlay.isChecked == true) { //or mediaPlayer?.isPlaying == false
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(this, R.raw.busker_loneliness_amplifier)

                }
                binding.btnPlay.setBackgroundResource(R.drawable.ic_pausemusic)
                mediaPlayer?.start()
            } else {
                binding.btnPlay.setBackgroundResource(R.drawable.ic_startmusic)
                mediaPlayer?.pause()

            }

        }


        /*binding.btnPrev.setOnClickListener {
            mediaPlayer?.pause()
            mediaPlayer = MediaPlayer.create(this, R.raw.busker_firstlove)
            mediaPlayer?.start()
        }
        binding.btnNext.setOnClickListener {
            if (mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
            mediaPlayer = MediaPlayer.create(this, R.raw.iu_cant_love_you_anymore)
            mediaPlayer?.start()
            binding.btnPlay.setBackgroundResource(R.drawable.ic_pausemusic)
        }*/


        // 노래 가사 출력 - jsoup
        Thread(Runnable {
            val builder = StringBuilder()
            val url = "https://www.genie.co.kr/detail/songInfo?xgnm=81302357"
            val doc = Jsoup.connect(url).get()
            val link = doc.select("#pLyrics").select("p").text()

            builder.append(link).append(" ")

            runOnUiThread(){
                binding.tvMusic.setText(builder.toString())
                //text 흐르게 출력
                binding.tvMusic.isSelected = true
            }
        }).start()

    }


    //백그라운드로 넘어갔을 때 음악 멈춤
    override fun onStop(){
        super.onStop()
        mediaPlayer?.release()
            mediaPlayer = null
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()

    }
}