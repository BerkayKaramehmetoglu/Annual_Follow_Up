import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.annual_follow_up.R
import com.example.annual_follow_up.sqlite.FollowUp

class RVAdapterPastProduct(
    private val context: Context,
    private var getAllProducts: List<FollowUp>
) :
    RecyclerView.Adapter<RVAdapterPastProduct.CardViewHolder>() {

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productDate: TextView = view.findViewById(R.id.productDate)
        val productName: TextView = view.findViewById(R.id.productName)
        val productAmount: TextView = view.findViewById(R.id.productAmount)
        val productSales: TextView = view.findViewById(R.id.productSales)
        val productExpense: TextView = view.findViewById(R.id.productExpense)
        val productEarning: TextView = view.findViewById(R.id.productEarning)
        val productDesc: TextView = view.findViewById(R.id.productDesc)
        val id: TextView = view.findViewById(R.id.productId)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.past_product_card, parent, false)
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return getAllProducts.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val product = getAllProducts[position]

        holder.productDate.text = product.productDate
        holder.productName.text = product.productName
        holder.productAmount.text = product.productAmount.toString().plus(" " + product.productType)
        holder.productSales.text = product.productSales.toString()
        holder.productExpense.text = product.productExpense.toString()
        holder.productEarning.text = product.productEarning.toString()
        holder.productDesc.text = product.productDesc
        holder.id.text = product.productId.toString()

        setProductEarningColor(holder.productEarning, product.productEarning)
    }

    private fun setProductEarningColor(productEarningView: TextView, productEarning: Int) {
        val color = when {
            productEarning < 0 -> ContextCompat.getColor(context, R.color.red)
            productEarning == 0 -> ContextCompat.getColor(context, R.color.black)
            else -> ContextCompat.getColor(context, R.color.green)
        }
        productEarningView.setTextColor(color)
    }
}
