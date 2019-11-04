import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { NetworkConfigurationUpdateComponent } from 'app/entities/network-configuration/network-configuration-update.component';
import { NetworkConfigurationService } from 'app/entities/network-configuration/network-configuration.service';
import { NetworkConfiguration } from 'app/shared/model/network-configuration.model';

describe('Component Tests', () => {
  describe('NetworkConfiguration Management Update Component', () => {
    let comp: NetworkConfigurationUpdateComponent;
    let fixture: ComponentFixture<NetworkConfigurationUpdateComponent>;
    let service: NetworkConfigurationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [NetworkConfigurationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(NetworkConfigurationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NetworkConfigurationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NetworkConfigurationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new NetworkConfiguration(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new NetworkConfiguration();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
