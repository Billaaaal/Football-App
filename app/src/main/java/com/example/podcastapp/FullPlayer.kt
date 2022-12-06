package com.example.podcastapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.podcastsapp.MainActivity.Companion.mediaplayer
import java.util.concurrent.TimeUnit


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FullPlayer.newInstance] factory method to
 * create an instance of this fragment.
 */
class FullPlayer : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_full_player, container, false)
        var play_button =  view.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.play_button)
        var seek_bar = view.findViewById<SeekBar>(R.id.seekBar)
        var timer_text = view.findViewById<TextView>(R.id.timer_text)
        var timer_total_text = view.findViewById<TextView>(R.id.timer_total_text)


        // Log.i("SUI2","${seek_bar.progress}")








        var currentPosition = mediaplayer.getCurrentPosition();
        var total = mediaplayer.getDuration();
        timer_total_text.text = convert_time(total)

        //mediaplayer.setPlaybackParams(mediaplayer.getPlaybackParams().setSpeed(5f));




        object : Thread() {
            override fun run() {
                super.run()
                while (mediaplayer.isPlaying()) {
                    currentPosition = try {
                        Thread.sleep(900)
                        currentPosition = mediaplayer.getCurrentPosition();
                       // Log.i("SUI2","${seek_bar.progress}")

                        timer_text.text = convert_time(currentPosition)

                        var position = (((currentPosition.toFloat()/1000)/(total.toFloat()/1000))*100)
                        Log.i("SUI",  "${position}")
                        seek_bar.setProgress(position.toInt())
                        Log.i("", "")

                    } catch (e: InterruptedException) {
                        Log.i("", "")
                    } catch (e: Exception) {
                        Log.i("", "")

                    }



                }
                // do your stuff here after the loop is finished
            }
        }.start()


        seek_bar?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                //Toast.makeText(view.context, "${((seek.progress/100)* mediaplayer.duration)}",Toast.LENGTH_SHORT).show()
                Log.i("HELLO", "${(seek.progress.toFloat()/100)}")

                mediaplayer.seekTo(((seek.progress.toFloat()/100)* mediaplayer.duration).toInt())
            }
        })

        var playing = 1

        if (playing == 1){
            play_button.setBackgroundResource(R.drawable.ic_pause)
            //Toast.makeText(view.context, "Pause !", Toast.LENGTH_SHORT).show()


        }else{
            play_button.setBackgroundResource(R.drawable.ic_play)
            //Toast.makeText(view.context, "Play !", Toast.LENGTH_SHORT).show()


        }



        play_button.setOnClickListener(){
            if (playing == 1){
                mediaplayer.pause()
                play_button.setBackgroundResource(R.drawable.ic_play)
                playing = 0

                //Toast.makeText(view.context, "Pause !", Toast.LENGTH_SHORT).show()


            }else{
                mediaplayer.start()
                play_button.setBackgroundResource(R.drawable.ic_pause)
                playing = 1

                //Toast.makeText(view.context, "Play !", Toast.LENGTH_SHORT).show()


            }




        }

        



        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FullPlayer.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FullPlayer().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun convert_time(milliseconds:Int):String {

        var milliseconds = milliseconds.toLong()

        // long minutes = (milliseconds / 1000) / 60;
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)

        // long seconds = (milliseconds / 1000);
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)

        if (seconds.toString().length == 1){
            return "${minutes.toString().take(2)}:0${seconds.toString().take(2)}"

        }else{
            return "${minutes.toString().take(2)}:${seconds.toString().take(2)}"

        }




    }
}