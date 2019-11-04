import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ProvisionercloudTestModule } from '../../../test.module';
import { RetransmitDeleteDialogComponent } from 'app/entities/retransmit/retransmit-delete-dialog.component';
import { RetransmitService } from 'app/entities/retransmit/retransmit.service';

describe('Component Tests', () => {
  describe('Retransmit Management Delete Component', () => {
    let comp: RetransmitDeleteDialogComponent;
    let fixture: ComponentFixture<RetransmitDeleteDialogComponent>;
    let service: RetransmitService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [RetransmitDeleteDialogComponent]
      })
        .overrideTemplate(RetransmitDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(RetransmitDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RetransmitService);
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
