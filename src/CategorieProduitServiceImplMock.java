package tn.esprit.spring.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.rh.achat.entities.CategorieProduit;
import tn.esprit.rh.achat.repositories.CategorieProduitRepository;
import tn.esprit.rh.achat.services.CategorieProduitServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CategorieProduitServiceImplMock {

	@Mock
	CategorieProduitRepository categorieProduitRepository;
	
	@InjectMocks
	CategorieProduitServiceImpl categorieProduitService;
	
	CategorieProduit cp = new CategorieProduit((long) 1, "abc","cat1", null);
	
	List<CategorieProduit> lcp = new ArrayList<CategorieProduit>() {
		{
		add(new CategorieProduit((long) 2, "abcd","cat2", null));
		add(new CategorieProduit((long) 3, "abcdf","cat3", null));
		}
	};

	
	@Test
	void testRetrieveAllCategorieProduits() {
		 
		Mockito.doReturn(lcp).when(categorieProduitRepository).findAll();
	    List<CategorieProduit> actualProducts = categorieProduitService.retrieveAllCategorieProduits();
	    assertThat(actualProducts).isEqualTo(lcp);
	}

	@Test
	void testAddCategorieProduit() {
        //CategorieProduit catP = categorieProduitService.addCategorieProduit(cp);
        //assertThat(catP).isNotNull();
		Mockito.when(categorieProduitRepository.save(Mockito.any(CategorieProduit.class))).thenReturn(cp);
		CategorieProduit NewCP = categorieProduitService.addCategorieProduit(cp) ;
		assertNotNull(NewCP);
		assertEquals(NewCP, cp);

	}

	@Test
	void testDeleteCategorieProduit()  {
		categorieProduitService.deleteCategorieProduit((long) 1);;
		Mockito.verify(categorieProduitRepository, times(1)).deleteById((long) 1);
	}

	@Test
	void testUpdateCategorieProduit() {
		Mockito.when(categorieProduitRepository.save(Mockito.any(CategorieProduit.class))).thenReturn(cp);
		cp.setCodeCategorie("code");
		CategorieProduit exisitingCP = categorieProduitService.updateCategorieProduit(cp) ;
		
		assertNotNull(exisitingCP);
		assertEquals("code", cp.getCodeCategorie());
	}

	@Test
	void testRetrieveCategorieProduit() {
		Mockito.when(categorieProduitRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(cp));
		CategorieProduit cp1 = categorieProduitService.retrieveCategorieProduit((long)1);
		Assertions.assertNotNull(cp1);
	}
}