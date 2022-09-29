package ni.edu.uca.listadoprod

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.accept
import android.text.Editable
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.NonCancellable.cancel
import ni.edu.uca.listadoprod.dataadapter.ProductoAdapter
import ni.edu.uca.listadoprod.databinding.ActivityMainBinding
import ni.edu.uca.listadoprod.dataclass.Producto
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var listaProd = ArrayList<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        iniciar()
    }

    fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun limpiar() {
        with(binding) {
            etID.setText("")
            etNombre.setText("")
            etPrecio.setText("")
            etID.requestFocus()
        }
    }

    private fun agregarProd() {
        with(binding) {
            try {
                val id: Int = etID.text.toString().toInt()
                val nombre: String = etNombre.text.toString()
                val precio: Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id, nombre, precio)
                listaProd.add(prod)
            } catch (ex: Exception) {
                Toast.makeText(
                    this@MainActivity, "Error: ${ex.toString()} ",
                    Toast.LENGTH_LONG
                ).show()
            }
            rcvLista.layoutManager = LinearLayoutManager(this@MainActivity)
            rcvLista.adapter = ProductoAdapter(listaProd,
                { producto -> selectProd(producto) },
                { position -> elimProd(position) },
                { position -> updateProd(position) })
            limpiar()
        }
    }

    fun selectProd(producto: Producto) {
        with(binding) {
            etID.text = producto.id.toString().toEditable()
            etNombre.text = producto.nombre.toEditable()
            etPrecio.text = producto.precio.toString().toEditable()
        }
    }

    fun updateProd(position: Int) {
        try {
            with(binding) {
                val id: Int = etID.text.toString().toInt()
                val nombre: String = etNombre.text.toString()
                val precio: Double = etPrecio.text.toString().toDouble()
                val prod = Producto(id, nombre, precio)
                listaProd[position] = prod
                rcvLista.adapter?.notifyItemChanged(position)
            }
        } catch (ex: Exception) {
            MaterialAlertDialogBuilder(this@MainActivity)
                .setTitle(getString(R.string.error_edit_title))
                .setMessage(getString(R.string.error_edit_body))
                .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }
        limpiar()
    }

    fun elimProd(position: Int) {
        MaterialAlertDialogBuilder(this@MainActivity)
            .setTitle(getString(R.string.Title_Elim_Prod))
            .setMessage(getString(R.string.Message_Elim_Prod))
            .setNeutralButton(getString(R.string.Cancelar)) { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton(getString(R.string.Si)) { dialog, which ->
                with(binding) {
                    listaProd.removeAt(position)
                    rcvLista.adapter?.notifyItemRemoved(position)
                }
            }
            .show()
        limpiar()
    }

    fun ApagarButton(button: Button) {
        button.isEnabled = false
    }

    private fun iniciar() {
        binding.btnAgregar.setOnClickListener {
            agregarProd()
        }
        binding.btnLimpiar.setOnClickListener {
            limpiar()
        }
    }
}