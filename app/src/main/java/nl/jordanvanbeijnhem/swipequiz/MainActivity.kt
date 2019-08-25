package nl.jordanvanbeijnhem.swipequiz

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import nl.jordanvanbeijnhem.swipequiz.adapter.QuestionAdapter
import nl.jordanvanbeijnhem.swipequiz.model.Question

class MainActivity : AppCompatActivity() {

    private val questions = arrayListOf<Question>()
    private val questionAdapter = QuestionAdapter(questions)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        rvQuestions.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        rvQuestions.adapter = questionAdapter
        rvQuestions.addItemDecoration(
            DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL
            )
        )

        for (i in Question.QUESTIONS.indices) {
            questions.add(Question(Question.QUESTIONS[i], Question.ANSWERS[i]))
        }
        questionAdapter.notifyDataSetChanged()

        createItemTouchHelper().attachToRecyclerView(rvQuestions)
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                if ((direction == ItemTouchHelper.LEFT && questions[position].answer) ||
                    (direction == ItemTouchHelper.RIGHT && !questions[position].answer)
                ) {
                    Snackbar.make(
                        rvQuestions,
                        getString(R.string.correct, questions[position].answer),
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    Snackbar.make(
                        rvQuestions,
                        getString(R.string.incorrect, questions[position].answer),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

                questionAdapter.notifyItemChanged(position)
            }
        }

        return ItemTouchHelper(callback)
    }
}
