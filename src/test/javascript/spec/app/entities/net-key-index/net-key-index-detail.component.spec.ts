import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProvisionercloudTestModule } from '../../../test.module';
import { NetKeyIndexDetailComponent } from 'app/entities/net-key-index/net-key-index-detail.component';
import { NetKeyIndex } from 'app/shared/model/net-key-index.model';

describe('Component Tests', () => {
  describe('NetKeyIndex Management Detail Component', () => {
    let comp: NetKeyIndexDetailComponent;
    let fixture: ComponentFixture<NetKeyIndexDetailComponent>;
    const route = ({ data: of({ netKeyIndex: new NetKeyIndex(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ProvisionercloudTestModule],
        declarations: [NetKeyIndexDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(NetKeyIndexDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NetKeyIndexDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.netKeyIndex).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
