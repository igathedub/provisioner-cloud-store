import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ProvisionercloudTestModule } from '../../../test.module';
import { ElementDeleteDialogComponent } from 'app/entities/element/element-delete-dialog.component';
import { ElementService } from 'app/entities/element/element.service';

describe('Component Tests', () => {
  describe('Element Management Delete Component', () => {
    let comp: ElementDeleteDialogComponent;
    let fixture: ComponentFixture<ElementDeleteDialogComponent>;
    let service: ElementService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [ElementDeleteDialogComponent]
      })
        .overrideTemplate(ElementDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ElementDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ElementService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
