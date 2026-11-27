package com.example.usandosqlite

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.usandosqlite.database.DatabaseHandler
import com.example.usandosqlite.databinding.ActivityMainBinding
import com.example.usandosqlite.entity.Cadastro

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var banco: DatabaseHandler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        banco = DatabaseHandler(this)


        binding.btIncluir.setOnClickListener {
            btIncluirOnClick()
        }
        binding.btAlterar.setOnClickListener {
            btAlterarOnClick()
        }
        binding.btExcluir.setOnClickListener {
            btExcluirOnClick()
        }
        binding.btPesquisar.setOnClickListener {
            btPesquisarOnClick()
        }
        binding.btListar.setOnClickListener {
            btListarOnClick()
        }

    }

    fun btIncluirOnClick() {
        val cadastro = Cadastro(
            id = 0,
            nome = binding.etNome.text.toString(),
            telefone = binding.etTelefone.text.toString()
        )
        banco.inserir(cadastro)

        Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show()
    }

    fun btAlterarOnClick() {
        val cadastro = Cadastro (
            id = binding.etCod.text.toString().toInt(),
            nome = binding.etNome.text.toString(),
            telefone = binding.etTelefone.text.toString()
        )
        banco.alterar(cadastro)

        Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show()

    }

    fun btExcluirOnClick() {
        banco.excluir(binding.etCod.toString().toInt())

        Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show()
    }

    fun btPesquisarOnClick() {
        val cadastro = banco.pesquisar(binding.etCod.text.toString().toInt())

        if (cadastro != null) {
            binding.etNome.setText(cadastro.nome)
            binding.etTelefone.setText(cadastro.telefone)
        } else {
            Toast.makeText(this, "Registro no encontrado", Toast.LENGTH_SHORT).show()
        }
    }

    fun btListarOnClick() {
        val registros = banco.listar()

        val saida = StringBuilder()

        registros.forEach { cadastro ->
            saida.append(cadastro.nome)
            saida.append(" - ")
            saida.append(cadastro.telefone)
            saida.append("\n")
        }
        Toast.makeText(
            this,
            saida.toString(),
            Toast.LENGTH_SHORT
        )
            .show()
    }

    companion object {
        private const val ID = 0
        private const val NOME = 1
        private const val TELEFONE = 2

    }
}