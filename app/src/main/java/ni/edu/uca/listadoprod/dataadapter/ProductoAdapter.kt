package ni.edu.uca.listadoprod.dataadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.listadoprod.databinding.ItemlistaBinding
import ni.edu.uca.listadoprod.dataclass.Producto

class ProductoAdapter(
    val listaProd: List<Producto>, private val onClickVer: (Producto) -> Unit,
    private val onClickEliminar: (Int) -> Unit,
    private val onClickActualizar: (Int) -> Unit
) :
    RecyclerView.Adapter<ProductoAdapter.ProductoHolder>() {
    inner class ProductoHolder(val binding: ItemlistaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun cargar(
            producto: Producto, onClickListener: (Producto) -> Unit,
            onClickDelete: (Int) -> Unit,
            onClickUpdate: (Int) -> Unit
        ) {
            with(binding) {
                tvCodProd.text = producto.id.toString()
                tvNombreProd.text = producto.nombre
                tvPrecioProd.text = producto.precio.toString()
                itemView.setOnClickListener { onClickListener(producto) }
                btnEditarProd.setOnClickListener { onClickUpdate(adapterPosition) }
                btnElimProd.setOnClickListener { onClickDelete(adapterPosition) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoHolder {
        val binding = ItemlistaBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )
        return ProductoHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoAdapter.ProductoHolder, position: Int) {
        holder.cargar(listaProd[position], onClickVer, onClickEliminar, onClickActualizar)
    }

    override fun getItemCount(): Int = listaProd.size
}