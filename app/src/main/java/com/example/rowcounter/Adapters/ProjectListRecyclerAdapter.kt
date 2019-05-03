package com.example.rowcounter.Adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.example.rowcounter.*
import kotlinx.android.synthetic.main.project_list_item.view.*

class ProjectListRecyclerAdapter(private val projects: ArrayList<String>) : RecyclerView.Adapter<ProjectListRecyclerAdapter.ProjectHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        val inflatedView = parent.inflate(R.layout.project_list_item, false)
        return ProjectHolder(inflatedView)

    }

    override fun getItemCount(): Int {
        return projects.size
    }

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        val project = projects[position]
        holder.bindProject(project, position)
    }


    //1
    inner class ProjectHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var projectName: String? = null
        private var position: Int? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("ProjectRecyclerView", "CLICK $projectName")
            val context = v.context
            val intent = Intent(context, ProjectActivity::class.java)
            intent.putExtra(EXTRA_PROJECT_NAME, projectName)
            intent.putExtra(EXTRA_PROJECT_POSITION, position)
            //context.startActivity(intent)
            (context as MainActivity).startActivityForResult(intent, DELETED_REQUEST)
        }

        fun bindProject(project: String, position: Int) {
            this.projectName = project
            this.position = position
            view.project_name.text = projectName
        }

    }

}

