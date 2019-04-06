package kr.ac.hansung.cse.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private ProductService productService;

	// 위의 requestMapping은 클래스 레벨, 아래는 메소드 레벨.
	@RequestMapping
	public String admingPage() {
		return "admin";
	}

	@RequestMapping("/productInventory")
	public String getProducts(Model model) { // controller --> model--> view
		List<Product> products = productService.getProducts();
		model.addAttribute("products", products);

		return "productInventory";

	}

	@RequestMapping(value = "/productInventory/addProduct", method = RequestMethod.GET)
	public String addProduct(Model model) {

		Product product = new Product();
		product.setCategory("컴퓨터");
		model.addAttribute("product", product); // 새로 만든 객체를 넘기고 폼은 저 카테고리정보를 바탕으로 채워진다??

		return "addProduct";
	}

	@RequestMapping(value = "/productInventory/addProduct", method = RequestMethod.POST)
	public String addProductPost(@Valid Product product, BindingResult result) { // 객체가 새로 만들어진다.(바인딩 되어서 만들어짐)
		// valid에 의해 검증된 후 결과가 bindingResult로 넘어오게 된다.
		if (result.hasErrors()) {
			System.out.println("form data has some errors ");
			List<ObjectError> errors = result.getAllErrors();

			for (ObjectError error : errors) {
				System.out.println(error.getDefaultMessage()); // Product.java에 선언해놓은 message들이 출력될것
			}
			return "addProduct";
		}

		if (!productService.addProduct(product)) // db에 넣는 작업임
			System.out.println("Adding product cannot be done");

		System.out.println(product);
		return "redirect:/admin/productInventory";
		// 결국 위의 RequestMapping(productInventory) 으로 가게되어 새로 페이지 로딩함.
		// 이렇게 하는 이유는 db저장하고 productInventory 뷰네임을 리턴하게 되면
		// 그 뷰는 model을 보유하고 있지 않다. 한마디로 보여줄게 음슴..
		// addProduct.jsp의 태그들의 path 이름과 java Product 빈즈의 변수명과 일치해야 바인딩된다.
	}

	@RequestMapping(value = "/productInventory/deleteProduct/{id}", method = RequestMethod.GET)
	public String deleteProducts(@PathVariable int id) { // 위의 id가 인자로 들어오게 된다.

		if (!productService.deleteProduct(id)) {
			System.out.println("Deleteting product cannot be done");
		}

		return "redirect:/admin/productInventory";

	}

	@RequestMapping(value = "/productInventory/updateProduct/{id}", method = RequestMethod.GET)
	public String updateProducts(@PathVariable int id, Model model) { // 위의 id가 인자로 들어오게 된다.

		Product product = productService.getProductById(id);

		model.addAttribute("product", product);
		// 이걸 해야 updateProduct.jsp 페이지의 폼태그들에 데이터가 바인딩 된다.

		return "updateProduct";

	}

	@RequestMapping(value = "/productInventory/updateProduct", method = RequestMethod.POST)
	public String updateProductPost(@Valid Product product,BindingResult result) { // 객체가 새로 만들어진다.(바인딩 되어서 만들어짐)

		// System.out.println(product);

		if (result.hasErrors()) {
			System.out.println("form data has some errors ");
			List<ObjectError> errors = result.getAllErrors();

			for (ObjectError error : errors) {
				System.out.println(error.getDefaultMessage()); // Product.java에 선언해놓은 message들이 출력될것
			}
			return "updateProduct";
		}

		if (!productService.updateProduct(product)) // db에 넣는 작업임
			System.out.println("Updating product cannot be done");

		return "redirect:/admin/productInventory";
		// 결국 위의 RequestMapping(productInventory) 으로 가게되어 새로 페이지 로딩함.
		// 이렇게 하는 이유는 db저장하고 productInventory 뷰네임을 리턴하게 되면
		// 그 뷰는 model을 보유하고 있지 않다. 한마디로 보여줄게 음슴..
		// addProduct.jsp의 태그들의 path 이름과 java Product 빈즈의 변수명과 일치해야 바인딩된다.
	}

}
