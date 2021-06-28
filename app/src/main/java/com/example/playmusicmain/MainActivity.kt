package com.example.playmusicmain

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import com.example.playmusicmain.databinding.ActivityMainBinding
import android.os.Bundle
import android.os.PersistableBundle
import org.jsoup.Jsoup
import org.jsoup.select.Elements

class MainActivity : AppCompatActivity() {

    // 전역 변수로 바인딩 객체 선언
    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    private var mediaPlayer: MediaPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


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


        /*binding.btnList.setOnClickListener{
           setFragment()
        }*/



        //음악 리스트에서 음악 클릭 시
        // 노래 가사 출력 - jsoup
            Thread(Runnable {
                val builder = StringBuilder()
                val url = "https://www.genie.co.kr/detail/songInfo?xgnm="
                val addr = "81302357"
                val doc = Jsoup.connect(url + addr).get()
                val link = doc.select("#pLyrics").select("p").text()

                val builder_info = StringBuilder()
                val link_info = doc.select(".name").text()

                builder.append(link).append(" ")
                builder_info.append(link_info).append(" ")

                runOnUiThread() {
                    binding.tvInfo.setText(builder_info.toString())
                    binding.tvInfo.isSelected = true

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