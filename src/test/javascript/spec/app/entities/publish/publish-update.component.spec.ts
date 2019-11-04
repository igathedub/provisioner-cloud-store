import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { PublishUpdateComponent } from 'app/entities/publish/publish-update.component';
import { PublishService } from 'app/entities/publish/publish.service';
import { Publish } from 'app/shared/model/publish.model';

describe('Component Tests', () => {
  describe('Publish Management Update Component', () => {
    let comp: PublishUpdateComponent;
    let fixture: ComponentFixture<PublishUpdateComponent>;
    let service: PublishService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [PublishUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(PublishUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PublishUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(PublishService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Publish(123);
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
        const entity = new Publish();
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
