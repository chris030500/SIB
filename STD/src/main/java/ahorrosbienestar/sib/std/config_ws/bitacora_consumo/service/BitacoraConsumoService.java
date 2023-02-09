package ahorrosbienestar.sib.std.config_ws.bitacora_consumo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import ahorrosbienestar.sib.std.config_ws.bitacora_consumo.model.BitacoraConsumo;
import ahorrosbienestar.sib.std.config_ws.bitacora_consumo.repository.IBitacoraConsumoRepository;
 
@Service
public class BitacoraConsumoService {
 
    @Autowired
    private IBitacoraConsumoRepository iBitacoraConsumoRepository;
    
    @Autowired
    public BitacoraConsumoService(IBitacoraConsumoRepository iBitacoraConsumoRepository) {
        this.iBitacoraConsumoRepository = iBitacoraConsumoRepository;
    }
    
    //Metodo para guardar en TBL_BITACORA_CONSUMO
    public BitacoraConsumo save(BitacoraConsumo bitacoraConsumo) {
        return iBitacoraConsumoRepository.save(bitacoraConsumo);
    }
    
    //Metodo para buscar por id en TBL_BITACORA_CONSUMO
    public Optional<BitacoraConsumo> findById(Integer id){
        return iBitacoraConsumoRepository.findById(id);
    }
    
}